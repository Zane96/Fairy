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
package me.zane.fairy.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.widget.ScrollView;
import android.widget.TextView;

import me.zane.fairy.R;


/**
 * Created by Zane on 2017/12/3.
 * Email: zanebot96@gmail.com
 */

public class BtnBindingAdapter {

    @BindingAdapter("fab_click")
    public static void onFabClick(FloatingActionButton btn, int id) {
        btn.setOnClickListener(v -> {
            TextView textView = btn.getRootView().findViewById(R.id.text_data_logcat);
            ScrollView scrollView = btn.getRootView().findViewById(R.id.scrollview_logcat);
            scrollView.smoothScrollTo(0, textView.getHeight());
        });
    }
}
