/*
 * Copyright (C) 2017 Zane.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zane.fairy.api;

import me.zane.fairy.Utils;
import me.zane.fairy.ZLog;
import me.zane.fairy.resource.ContentNetResource;
import rx.Observable;

/**
 * Created by Zane on 2017/10/30.
 * Email: zanebot96@gmail.com
 */

public class ObservaleCreater {
    static final String DEFAULT_TIMELINE = "null";
    static String lastTimeLine = DEFAULT_TIMELINE; //从服务端获取的最后一个有效时间戳
    static String startTimeLine = DEFAULT_TIMELINE;//最后一次用来开始获取数据的时间戳
    static boolean isPoll = false;//是否需要进行轮询

    static NullObCreater nullObCreater;
    static PollObCreater pollObCreater;

    static {
        nullObCreater = new NullObCreater();
        pollObCreater = new PollObCreater();
    }

    /**
     * NullObCreater中的空Observable是为了去重，有时间server响应太慢会导致连续发送多个相同的请求
     * 所以需要在这里分发的时候，根据不同情况：
     * 1. 正常请求数据
     * 2. 请求去重
     * 3. 正常轮询新数据 （上一次服务端没有给新数据）
     *
     * 每一次请求开始的时候：${startTimeLine} == ${lastTimeLine} + 1 millseconds
     * 每一次请求结束的时候：
     * 1. 如果服务端没有给数据，那么${startTimeLine} == ${lastTimeLine} + 1 millseconds仍然成立，但是在onNext中
     * 需要打上轮询的标志
     * 2. 服务端给了正常数据，所以上面的等式不成立，${lastTimeLine}肯定会大于${startTimeLine}
     * @param options
     * @param filter
     * @return
     */
    public Observable<LogcatData> creatObservable(String options, String filter) {
        //需要timeline的feed流
        Observable<LogcatData> observable;
        //这种情况是可能是初始的第一次正常请求数据
        ZLog.d(startTimeLine + " " + lastTimeLine + " " + isPoll);
        if (startTimeLine.equals(lastTimeLine)) {
            observable = pollObCreater.creat(options, filter);
        } else {
            //表示第一次向服务器拿数据就已经是空，所以应该继续请求数据
            if (lastTimeLine.equals(DEFAULT_TIMELINE)) {
                observable = pollObCreater.creat(options, filter);
            } else {
                //${startTimeLine} == ${lastTimeLine} + 1 millseconds等式成立，但是没有轮询标志，应该去重
                if (startTimeLine.equals(Utils.addOneMillsecond(lastTimeLine)) && !isPoll) {
                    observable = nullObCreater.creat();
                } else {
                    //正常轮询
                    observable = pollObCreater.creat(options, filter);
                }
            }
        }

        return observable;

//        return pollObCreater.creat(options, filter).filter(new Func1<LogcatData, Boolean>() {
//            @Override
//            public Boolean call(LogcatData logcatData) {
//                if (!lastTimeLine.equals(DEFAULT_TIMELINE)) {
//                    if (startTimeLine.equals(Utils.addOneMillsecond(lastTimeLine)) && !isPoll) {
//                        return false;
//                    }
//                }
//                return true;
//            }
//        });
    }

    /**
     *
     * @param logcatData
     * @param callBack
     */
    public void onNext(LogcatData logcatData, ContentNetResource.DataCallBack callBack) {
        ZLog.d(logcatData.getTimeLine() + " timeline");
        //如果轮训的时候数据为空，那么返回的时间戳也是空
        if (!logcatData.getTimeLine().equals(DEFAULT_TIMELINE)
                    && !logcatData.getTimeLine().equals("")) {
            lastTimeLine = logcatData.getTimeLine();
            //取消新数据的空数据轮训状态
            isPoll = false;
            //callBack.onSuccess(logcatData);
        }

        if (logcatData.getTimeLine().equals("")) {
            isPoll = true;
        }
    }

}
