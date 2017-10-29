package me.zane.fairy;

import android.app.Application;
import android.content.Context;

/**
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public class App extends Application{
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MySharedPre.getInstance().putIpAddress(Utils.getIpAddress());
    }

    public static Context getInstance() {
        return instance;
    }
}
