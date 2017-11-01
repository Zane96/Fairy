package me.zane.fairy;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public class MySharedPre {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private MySharedPre() {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        editor = preferences.edit();
    }

    private static final class SingletonHolder {
        private static final MySharedPre instance = new MySharedPre();
    }

    public static MySharedPre getInstance() {
        return SingletonHolder.instance;
    }

    public void putIpAddress(String ipAddress) {
        editor.putString("ipaddress", ipAddress);
        editor.commit();
    }

    public String getIpAddress(String defaultValue) {
        return preferences.getString("ipaddress", defaultValue);
    }

    public void putOptions(int index, String options) {
        editor.putString(String.format("%d_options", index), options);
        editor.commit();
    }

    public String getOptions(int index, String defaultValue) {
        return preferences.getString(String.format("%d_options", index), defaultValue);
    }

    public void putFilter(int index, String filter) {
        editor.putString(String.format("%d_filter", index), filter);
        editor.commit();
    }

    public String getFilter(int index, String defaultValue) {
        return preferences.getString(String.format("%d_filter", index), defaultValue);
    }
}
