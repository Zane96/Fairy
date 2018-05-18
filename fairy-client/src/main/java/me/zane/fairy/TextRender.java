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

/**
 * Created by Zane on 2017/12/17.
 * Email: zanebot96@gmail.com
 */


import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.Executor;

import me.zane.fairy.custom.TouchStopRecycleView;
import me.zane.fairy.resource.AppExecutors;
import me.zane.fairy.view.content.LogcatAdapter;

/**
 * 多线程渲染
 * Created by Zane on 2017/11/29.
 * Email: zanebot96@gmail.com
 */

public class TextRender {
    private static final Executor renderExecutors;
    private static final Executor mainEecutors;
    private static final Handler handler;

    private TextRender() {}

    static {
        renderExecutors = AppExecutors.getInstance().getFastTask();
        mainEecutors = AppExecutors.getInstance().getMainExecutor();
        handler = new Handler();
    }

    public static void renderText(TextView textView, CharSequence rawText, boolean isFirst) {
//        ProgressBar progressBar = rootView.findViewById(R.id.progressbar_logcat);
//
//        if (isFirst) {
//            progressBar.setVisibility(View.VISIBLE);
//        }

        renderExecutors.execute(() -> {
            CharSequence text = Html.fromHtml(rawText.toString());
            mainEecutors.execute(() -> {
                textView.setText(text);
//                if (isFirst) {
//                    textView.setText(text);
//                    rootView.findViewById(R.id.btn_globa).setEnabled(true);
//                    rootView.findViewById(R.id.btn_start_logcat).setEnabled(true);
//                    //progressBar.setVisibility(View.GONE);
//                } else {
//                    textView.append(text);
//                }


            });
        });
    }
}
