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
package me.zane.fairy_api.api;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.concurrent.TimeUnit;

import me.zane.fairy.ZLog;
import me.zane.fairy.resource.ApiResponse;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Zane on 2017/10/27.
 * Email: zanebot96@gmail.com
 */

public class LiveNetService {
    private Observable<Long> timer;
    private Subscription subscription;
    private ObservaleCreater observaleCreater;
    private boolean isClose = false;

    private String grep = "";

    private MutableLiveData<ApiResponse<String>> data;

    LiveNetService() {
        timer = Observable.interval(500, TimeUnit.MILLISECONDS);
        observaleCreater = new ObservaleCreater();
        data = new MutableLiveData<>();
    }

    public LiveData<ApiResponse<String>> getData() {
        return data;
    }

    public void setGrep(String grep) {
        this.grep = grep;
    }

    /**
     * 按模式进行分发
     * @param options
     * @param filter
     */
    public void enqueue(final String options, final String filter) {
        if (options.contains("-m") || options.contains("--max-count")) {
            //不需要轮训，也不需要timeline的feed流
            subscription = LogcatModel.getInstance().logcat(options, filter, grep).subscribe(new Subscriber<LogcatData>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    data.setValue(new ApiResponse<>(e));
                    awaitToStop();
                }

                @Override
                public void onNext(LogcatData logcatData) {
                    data.setValue(new ApiResponse<>(logcatData.getData()));
                    awaitToStop();
                }
            });
        } else {
            //需要轮训
            subscription = timer.flatMap((Long aLong) -> observaleCreater.creatObservable(options, filter, grep)).subscribe(new Subscriber<LogcatData>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    ZLog.e(String.valueOf(e));
                    data.setValue(new ApiResponse<>(e));
                    awaitToStop();
                }

                @Override
                public void onNext(LogcatData logcatData) {
                    ZLog.i("logcat: " + logcatData.toString());
                    observaleCreater.onNext(logcatData);
                    data.setValue(new ApiResponse<>(logcatData.getData()));
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
