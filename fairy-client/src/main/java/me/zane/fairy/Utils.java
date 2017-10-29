package me.zane.fairy;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;

import java.io.File;

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
}
