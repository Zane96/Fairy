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
package me.zane.fairy.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Zane on 2017/11/28.
 * Email: zanebot96@gmail.com
 */

public class TouchStopScrollView extends ScrollView{
    public TouchStopScrollView(Context context) {
        super(context);
    }

    public TouchStopScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchStopScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSmoothScrollingEnabled(false);
                break;
            case MotionEvent.ACTION_UP:
                setSmoothScrollingEnabled(true);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
