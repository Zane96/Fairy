package me.zane.fairy.data;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import me.zane.fairy.NullIpAddressException;
import me.zane.fairy.ZLog;
import rx.Emitter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Zane on 2017/10/27.
 * Email: zanebot96@gmail.com
 */

public class DataEngine {
    private static final int TIME_INTERVAL = 500; //millseconds

    private Observable<Long> timer;
    private Subscription subscription;
    private ObservaleCreater observaleCreater;

    public interface DataCallBack {
        void onSuccess(LogcatData date);
        void onFailed(String error);
    }

    public DataEngine() {
        timer = Observable.interval(500, TimeUnit.MILLISECONDS);
        observaleCreater = new ObservaleCreater();
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
                    callBack.onFailed(e.getMessage());
                }

                @Override
                public void onNext(LogcatData logcatData) {
                    callBack.onSuccess(logcatData);
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
                    callBack.onFailed(e.getMessage());
                }

                @Override
                public void onNext(LogcatData logcatData) {
                    observaleCreater.onNext(logcatData, callBack);
                }
            });
        }
    }

    public void stop() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
