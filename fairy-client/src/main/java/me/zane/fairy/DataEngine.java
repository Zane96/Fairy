package me.zane.fairy;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by Zane on 2017/10/27.
 * Email: zanebot96@gmail.com
 */

public class DataEngine {
    private static final int TIME_INTERVAL = 500; //millseconds
    private static final String DEFAULT_TIMELINE = "null";
    private Observable<Long> timer;
    private Subscription subscription;
    private String lastTimeLine = DEFAULT_TIMELINE;
    private String startTimeLine = DEFAULT_TIMELINE;

    public interface DataCallBack {
        void onSuccess(LogcatData date);
        void onFailed(String error);
    }

    public DataEngine() {
        timer = Observable.interval(500, TimeUnit.MILLISECONDS);
    }

//    private static final class SingletonHolder {
//        private static final DataEngine instance = new DataEngine();
//    }
//
//    public static DataEngine getInstance() {
//        return SingletonHolder.instance;
//    }

    public void enqueue(DataCallBack callBack) throws NullIpAddressException{
        enqueue("", "", callBack);
    }

    /**
     * 需 重构 成 分发 的模式
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
            subscription = timer.flatMap(aLong -> {
                //需要timeline的feed流
                String newOptions = options;
                if (!lastTimeLine.equals(DEFAULT_TIMELINE)) {
                    if (!newOptions.contains("-t")) {
                        String timeLine = currentTimeLine(lastTimeLine);
                        newOptions = options + String.format(" -t %s", timeLine);
                    } else if (newOptions.contains("-t \"")) {
                        String timeLine = currentTimeLine(lastTimeLine);
                        String oldTime = options.substring(options.indexOf("-t \""), options.indexOf("-t \"") + 23);
                        newOptions = options.replace(oldTime, String.format("-t \"%s\"", timeLine));
                    }
                }
                //不需要timeline的feed流
                return LogcatModel.getInstance().logcat(newOptions, filter);
            }).subscribe(new Subscriber<LogcatData>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    callBack.onFailed(e.getMessage());
                }

                @Override
                public void onNext(LogcatData logcatData) {
                    lastTimeLine = logcatData.getTimeLine();
                    callBack.onSuccess(logcatData);
                }
            });
        }
    }

    public void stop() {
        MySharedPre.getInstance().putTimeLine(null);
        if (subscription != null) {
            subscription.unsubscribe();
        }
//        if (dataSubscription != null) {
//            dataSubscription.unsubscribe();
//        }
    }

    /**
     * lastTimeLine + 1 millseconds == currentTimeLine is true
     * eg.10-26 16:40:35.584
     * @param timeLine
     * @return
     */
    private String currentTimeLine(String timeLine) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Integer.valueOf(timeLine.substring(0, 2)));
        calendar.set(Calendar.DATE, Integer.valueOf(timeLine.substring(3, 5)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeLine.substring(6, 8)));
        calendar.set(Calendar.MINUTE, Integer.valueOf(timeLine.substring(9, 11)));
        calendar.set(Calendar.SECOND, Integer.valueOf(timeLine.substring(12, 14)));
        calendar.set(Calendar.MILLISECOND, Integer.valueOf(timeLine.substring(15, timeLine.length())));
        calendar.add(Calendar.MILLISECOND, 1);

        return String.format("%d-%d %d:%d:%d.%d",
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                calendar.get(Calendar.MILLISECOND));
    }
}
