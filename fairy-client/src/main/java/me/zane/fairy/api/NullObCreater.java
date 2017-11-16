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

import rx.Emitter;
import rx.Observable;

/**
 * Created by Zane on 2017/10/30.
 * Email: zanebot96@gmail.com
 */

class NullObCreater extends ObservaleCreater{

    private Observable observable;

    public NullObCreater() {
        observable = Observable.create(emitter -> {
            LogcatData data = new LogcatData();
            data.setTimeLine(DEFAULT_TIMELINE);
            emitter.onNext(data);
        }, Emitter.BackpressureMode.LATEST).share();
    }

    Observable<LogcatData> creat() {
        return observable;
    }
}
