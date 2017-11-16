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
package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import me.zane.fairy.NullIpAddressException;
import me.zane.fairy.ZLog;
import me.zane.fairy.api.LogcatData;
import me.zane.fairy.api.LogcatModel;
import me.zane.fairy.api.ObservaleCreater;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Zane on 2017/10/27.
 * Email: zanebot96@gmail.com
 */

public class ContentNetResource {
    private static final int TIME_INTERVAL = 500; //millseconds

    private Observable<Long> timer;
    private Subscription subscription;
    private ObservaleCreater observaleCreater;
    private boolean isClose = false;

    private final MutableLiveData<String> data;

    public interface DataCallBack {
        void onSuccess(LogcatData date);
        void onFailed(String error);
    }

    public ContentNetResource() {
        timer = Observable.interval(500, TimeUnit.MILLISECONDS);
        observaleCreater = new ObservaleCreater();
        data = new MutableLiveData<>();
    }

    public LiveData<String> getData() {
        return data;
    }

    /**
     * Logcat without any args
     * @param callBack
     * @throws NullIpAddressException
     */
    public void enqueue(DataCallBack callBack) throws NullIpAddressException {
        enqueue("", "", callBack);
    }

    /**
     * 按模式进行分发
     * @param options
     * @param filter
     * @param callBack
     * @throws NullIpAddressException
     */
    public void enqueue(final String options, String filter, DataCallBack callBack) {
        if (options.contains("-m") || options.contains("--max-count")) {
            //不需要轮训，也不需要timeline的feed流
            subscription = LogcatModel.getInstance().logcat(options, filter).subscribe(new Subscriber<LogcatData>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    //callBack.onFailed(e.getMessage());
                    data.setValue(e.getMessage());
                    awaitToStop();
                }

                @Override
                public void onNext(LogcatData logcatData) {
                    //callBack.onSuccess(logcatData);
                    data.setValue(logcatData.getData());
                    awaitToStop();
                }
            });
        } else {
            //需要轮训
            subscription = timer.flatMap((Long aLong) -> observaleCreater.creatObservable(options, filter)).subscribe(new Subscriber<LogcatData>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ZLog.e(String.valueOf(e));
                    //callBack.onFailed(e.getMessage());
                    data.setValue(e.getMessage());
                    awaitToStop();
                }

                @Override
                public void onNext(LogcatData logcatData) {
                    observaleCreater.onNext(logcatData, callBack);
                    data.setValue(logcatData.getData());
                    awaitToStop();
                }
            });
        }
    }

    private void awaitToStop() {
        if (isClose) {
            subscription.unsubscribe();
            isClose = false;
        }
    }

    public void stop() {
        if (subscription != null) {
            isClose = true;
        }
    }
}
