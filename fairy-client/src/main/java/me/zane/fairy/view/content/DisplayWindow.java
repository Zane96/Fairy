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
package me.zane.fairy.view.content;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import me.zane.fairy.R;

/**
 * Created by Zane on 2017/12/16.
 * Email: zanebot96@gmail.com
 */

public class DisplayWindow{
    private View rootView;
    private WindowManager wm;

    private Context context;
    private int pid;

    public DisplayWindow(Context context, int pid, long time) {
        this.context = context;
        this.pid = pid;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        initView(context);
    }

    /**
     * 启动渲染
     */
    public void start() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG;
        params.alpha = 0.5f;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                               | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                               | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                               | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        wm.addView(rootView, params);

        renderData();
    }

    /**
     * 停止渲染，remove窗口
     */
    public void stop() {
        wm.removeView(rootView);
    }


    /**
     * 刷新数据
     * 包括了窗口展示和文件输出
     */
    private void renderData() {

    }


    @SuppressLint("InflateParams")
    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.window_display, null);

    }
}
