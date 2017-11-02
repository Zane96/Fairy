package me.zane.fairy;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import java.io.File;
import java.util.Calendar;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public class Utils {
    public static File getDiskCacheDir(String uniqueName) {
        String cachePath;
        if(!"mounted".equals(Environment.getExternalStorageState()) && Environment.isExternalStorageRemovable()) {
            cachePath = App.getInstance().getCacheDir().getPath();
        } else {
            cachePath = App.getInstance().getExternalCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }

    public static String getIpAddress() {
        WifiManager wifiManager = (WifiManager) App.getInstance().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return (ipAddress & 0xff) + "." + (ipAddress>>8 & 0xff) + "." + (ipAddress>>16 & 0xff) + "." + (ipAddress >> 24 & 0xff);
    }

    public static void moveOneSp(int startPosition) {
        final String NULL_VALUE = "command_null";
        MySharedPre sp = MySharedPre.getInstance();
        int i = startPosition;
        while (true) {
            int next = i + 1;
            String options = sp.getOptions(next, NULL_VALUE);
            String filter = sp.getFilter(next, NULL_VALUE);
            if (options.equals(NULL_VALUE) || filter.equals(NULL_VALUE)) {
                sp.putFilter(i, null);
                sp.putOptions(i, null);
                break;
            }
            //前移
            sp.putOptions(i, options);
            sp.putFilter(i, filter);
            i++;
        }
    }

    public static void swapSp(int from, int to) {
        final MySharedPre sp = MySharedPre.getInstance();
        ZLog.d(from + " " + to);
        String options = sp.getOptions(from, "");
        String filter = sp.getFilter(from, "");
        sp.putOptions(from, sp.getOptions(to, ""));
        sp.putFilter(from, sp.getFilter(to, ""));
        sp.putOptions(to, options);
        sp.putFilter(to, filter);
    }

    /**
     * lastTimeLine + 1 millseconds == addOneMillsecond is true
     * eg.10-26 16:40:35.584
     * @param timeLine
     * @return
     */
    public static String addOneMillsecond(String timeLine) {
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
