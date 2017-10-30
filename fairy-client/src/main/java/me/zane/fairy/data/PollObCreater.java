package me.zane.fairy.data;

import java.util.Calendar;

import me.zane.fairy.ZLog;
import rx.Observable;

/**
 * Created by Zane on 2017/10/30.
 * Email: zanebot96@gmail.com
 */

public class PollObCreater extends ObservaleCreater{

    public Observable<LogcatData> creat(String options, String filter) {
        String newOptions = options;
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
        return LogcatModel.getInstance().logcat(newOptions, filter);
    }

    private String getStartTimeLine() {
        String timeLine;
        if (pollTimeLine.equals(DEFAULT_TIMELINE)) {
            timeLine = addOneMillsecond(lastTimeLine);
        } else {
            timeLine = lastTimeLine;
        }
        return timeLine;
    }

    /**
     * lastTimeLine + 1 millseconds == addOneMillsecond is true
     * eg.10-26 16:40:35.584
     * @param timeLine
     * @return
     */
    private String addOneMillsecond(String timeLine) {
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
