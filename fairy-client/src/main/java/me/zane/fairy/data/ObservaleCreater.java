package me.zane.fairy.data;

import me.zane.fairy.ZLog;
import rx.Observable;

/**
 * Created by Zane on 2017/10/30.
 * Email: zanebot96@gmail.com
 */

public class ObservaleCreater {
    static final String DEFAULT_TIMELINE = "null";
    static String lastTimeLine = DEFAULT_TIMELINE; //最后一次完成的时间戳
    static String startTimeLine = DEFAULT_TIMELINE;//最后一次开始的时间戳
    static String pollTimeLine = DEFAULT_TIMELINE;//等待新数据时候的轮训时间戳

    static NullObCreater nullObCreater;
    static PollObCreater pollObCreater;

    static {
        nullObCreater = new NullObCreater();
        pollObCreater = new PollObCreater();
    }

    /**
     *
     * @param options
     * @param filter
     * @return
     */
    Observable<LogcatData> creatObservable(String options, String filter) {
        //需要timeline的feed流
        if (startTimeLine.equals(lastTimeLine) && !startTimeLine.equals(DEFAULT_TIMELINE)) {
            return nullObCreater.creat();
        } else {
            return pollObCreater.creat(options, filter);
        }
    }

    /**
     *
     * @param logcatData
     * @param callBack
     */
    void onNext(LogcatData logcatData, DataEngine.DataCallBack callBack) {
        //如果轮训的时候数据为空，那么返回的时间戳也是空
        if (!logcatData.getTimeLine().equals(DEFAULT_TIMELINE)
                    && !logcatData.getTimeLine().equals("")) {
            lastTimeLine = logcatData.getTimeLine();
            //取消新数据的空数据轮训状态
            pollTimeLine = DEFAULT_TIMELINE;
            ZLog.i("getTimeLine: " + lastTimeLine);
            callBack.onSuccess(logcatData);
        }
        //如果返回的数据是空，那么就付之
        if (logcatData.getTimeLine().equals("")) {
            lastTimeLine = pollTimeLine;
        }
    }

}
