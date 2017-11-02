package me.zane.fairy.data;

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
