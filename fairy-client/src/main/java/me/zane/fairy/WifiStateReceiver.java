package me.zane.fairy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

/**
 * 监听Wifi连接状态，存储最新的内网IP地址
 * Created by Zane on 2017/10/26.
 * Email: zanebot96@gmail.com
 */

public class WifiStateReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                NetworkInfo.State state = networkInfo.getState();
                boolean isConnected = state == NetworkInfo.State.CONNECTED;
                if (isConnected) {
                    MySharedPre.getInstance().putIpAddress(Utils.getIpAddress());
                } else {
                    MySharedPre.getInstance().putIpAddress("0.0.0.0");
                }
            }
        }
    }
}
