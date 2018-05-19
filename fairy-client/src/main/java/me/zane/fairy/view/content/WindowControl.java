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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;

import me.zane.fairy.R;
import me.zane.fairy.TextRender;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2017/12/17.
 * Email: zanebot96@gmail.com
 */

public class WindowControl implements IWindowControl{
    private final Context context;
    private final WindowManager wm;
    private static final WindowManager.LayoutParams params;
    private final View rootView;
    private final Observer<LogcatContent> observer;
    private boolean isOpened = false;
    private LiveData<LogcatContent> data;
    private boolean isFirst = true;

    private RecyclerView mRecycleView;
    private LogcatAdapter mAdapter;

    static {
        params = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        }
        params.alpha = 0.7f;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                               | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                               | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                               | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
    }

    public WindowControl(Context context) {
        this.context = context;
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        rootView = LayoutInflater.from(context).inflate(R.layout.window_display, null);
        mRecycleView = (RecyclerView) rootView;
        mRecycleView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new LogcatAdapter(context);
        mRecycleView.setAdapter(mAdapter);

        observer = content -> {
            if (content != null) {
                if (isFirst) {
                    content.setContent("init window");
                }

                if (!TextUtils.isEmpty(content.getContent())) {
                    mAdapter.addData(content);
                    mRecycleView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                }

                isFirst = false;
            }
        };
    }

    @Override
    public void initData(LiveData<LogcatContent> data) {
        this.data = data;
    }

    @Override
    public void open() {
        wm.addView(rootView, params);
        data.observeForever(observer);
        isOpened = true;
    }

    @Override
    public void close() {
        if (isOpened) {
            wm.removeView(rootView);
            data.removeObserver(observer);
            isOpened = false;
            isFirst = true;
        }
    }
}
