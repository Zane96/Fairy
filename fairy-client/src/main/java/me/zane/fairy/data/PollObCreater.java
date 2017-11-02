package me.zane.fairy.data;

import java.util.Calendar;

import me.zane.fairy.Utils;
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
                newOptions = options + String.format(" -t \"%s\"", timeLine);
            } else if (newOptions.contains("-t \"")) {
                String timeLine = getStartTimeLine();
                String oldTime = options.substring(options.indexOf("-t \""), options.indexOf("-t \"") + 23);
                newOptions = options.replace(oldTime, String.format("-t \"%s\"", timeLine));
            }
        }
        return LogcatModel.getInstance().logcat(newOptions, filter);
    }

    private String getStartTimeLine() {
        String timeLine;
        timeLine = Utils.addOneMillsecond(lastTimeLine);
        startTimeLine = timeLine;

        return timeLine;
    }
}
