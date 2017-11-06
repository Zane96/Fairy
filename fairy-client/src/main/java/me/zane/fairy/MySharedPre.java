/*
 * Copyright (C) 2017 Zane.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
