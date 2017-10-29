package me.zane.fairy.data;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import me.zane.fairy.MySharedPre;
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
    private static final String DEFAULT_TIMELINE = "null";
    private Observable<Long> timer;
    private Subscription subscription;
    private String lastTimeLine = DEFAULT_TIMELINE; //最后一次完成的时间戳
    private String startTimeLine = DEFAULT_TIMELINE;//最后一次开始的时间戳
    private String pollTimeLine = DEFAULT_TIMELINE;//等待新数据时候的轮训时间戳

    public interface DataCallBack {
        void onSuccess(LogcatData date);
        void onFailed(String error);
    }

    public DataEngine() {
        timer = Observable.interval(500, TimeUnit.MILLISECONDS);
    }

    /**
     * Logcat without any arges
     * @param callBack
     * @throws NullIpAddressException
     */
    public void enqueue(DataCallBack callBack) throws NullIpAddressException {
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

                if (startTimeLine.equals(lastTimeLine) && !startTimeLine.equals(DEFAULT_TIMELINE)) {
                    return Observable.create(emitter -> {
                        LogcatData data = new LogcatData();
                        data.setTimeLine(DEFAULT_TIMELINE);
                        emitter.onNext(data);
                    }, Emitter.BackpressureMode.LATEST);
                } else {
                    if (!lastTimeLine.equals(DEFAULT_TIMELINE)) {
                        if (!newOptions.contains("-t")) {
                            String timeLine = getStartTimeLine();
                            pollTimeLine = timeLine;

                            newOptions = options + String.format(" -t \"%s\"", timeLine);
                        } else if (newOptions.contains("-t \"")) {
                            String timeLine = getStartTimeLine();
                            pollTimeLine = timeLine;

                            String oldTime = options.substring(options.indexOf("-t \""), options.indexOf("-t \"") + 23);
                            newOptions = options.replace(oldTime, String.format("-t \"%s\"", timeLine));
                        }
                    }
                    startTimeLine = lastTimeLine;
                    //不需要timeline的feed流
                    return LogcatModel.getInstance().logcat(newOptions, filter);
                }
            }).subscribe(new Subscriber<LogcatData>() {
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
            });
        }
    }

    public void stop() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    private String getStartTimeLine() {
        String timeLine;
        if (pollTimeLine.equals(DEFAULT_TIMELINE)) {
            timeLine = currentTimeLine(lastTimeLine);
        } else {
            timeLine = lastTimeLine;
        }
        return timeLine;
    }

    /**
     * lastTimeLine + 1 millseconds == currentTimeLine is true
     * eg.10-26 16:40:35.584
     * @param timeLine
     * @return
     */
    private String currentTimeLine(String timeLine) {
        ZLog.d("exec: " + timeLine);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Integer.valueOf(timeLine.substring(0, timeLine.indexOf("-"))));
        calendar.set(Calendar.DATE, Integer.valueOf(timeLine.substring(timeLine.indexOf("-") + 1, timeLine.indexOf(" "))));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeLine.substring(timeLine.indexOf(" ") + 1, timeLine.indexOf(":"))));
        calendar.set(Calendar.MINUTE, Integer.valueOf(timeLine.substring(timeLine.indexOf(":") + 1, timeLine.lastIndexOf(":"))));
        calendar.set(Calendar.SECOND, Integer.valueOf(timeLine.substring(timeLine.lastIndexOf(":") + 1, timeLine.indexOf("."))));
        calendar.set(Calendar.MILLISECOND, Integer.valueOf(timeLine.substring(timeLine.indexOf(".") + 1, timeLine.length())));
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
