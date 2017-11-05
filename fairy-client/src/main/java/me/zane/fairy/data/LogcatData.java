package me.zane.fairy.data;

/**
 * 纯Bean类
 * 在Result中根据-v过滤一遍，顺便过滤掉无用的timeLine
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public class LogcatData {

    /**
     * data : --------- beginning of main
     10-24 16:02:50.621 11262 11271 I DataEngine   : body2: options=-t 5&filter=&timeline=
     10-24 16:02:50.639 11262 11273 D DataEngine   : start exec
     10-24 16:02:50.639  1492  1852 E Parcel  : Reading a NULL string not supported here.
     10-24 16:02:50.640 11262 11271 I DataEngine   : Client Socket accept success from: /192.168.0.105
     10-24 16:02:50.720 11262 11273 I DataEngine   : exec logcat command: logcat -d -t 5

     * timeLine : 10-24 16:02:50.720
     */

    private String data;
    private String timeLine;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }


}
