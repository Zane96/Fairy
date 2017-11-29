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
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.Executor;

import me.zane.fairy.Config;
import me.zane.fairy.R;
import me.zane.fairy.resource.AppExecutors;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2017/11/20.
 * Email: zanebot96@gmail.com
 */

public class TextBindingAdapter {
    @BindingAdapter("render")
    public static void append(TextView view, LogcatContent content) {
        if (content != null) {
            CharSequence s = content.getContent();
            if (s.equals(Config.CLEAR_SIGNAL)) {
                view.setText("clear data");
            } else if (!s.equals("")) {
                TextRender.renderText(view, s, content.isFirst());
            }
        }
    }

    /**
     * 多线程渲染
     * Created by Zane on 2017/11/29.
     * Email: zanebot96@gmail.com
     */

    public static class TextRender {
        private static final Executor renderExecutors;
        private static final Executor mainEecutors;
        private static final Handler handler;

        private TextRender() {}

        static {
            renderExecutors = AppExecutors.getInstance().getFastTask();
            mainEecutors = AppExecutors.getInstance().getMainExecutor();
            handler = new Handler();
        }

        static void renderText(TextView textView, CharSequence rawText, boolean isFirst) {
            View rootView = textView.getRootView();
            ProgressBar progressBar = rootView.findViewById(R.id.progressbar_logcat);
            ScrollView scrollView = rootView.findViewById(R.id.scrollview_logcat);
            progressBar.setVisibility(View.VISIBLE);

            renderExecutors.execute(() -> {
                CharSequence text = Html.fromHtml(rawText.toString());
                mainEecutors.execute(() -> {
                    if (isFirst) {
                        textView.setText(text);
                    } else {
                        textView.append(text);
                    }
                    progressBar.setVisibility(View.GONE);

                    handler.postDelayed(() -> {
                        if (scrollView.isSmoothScrollingEnabled()) {
                            scrollView.smoothScrollTo(0, textView.getHeight());
                        }
                    }, 200);
                });
            });
        }
    }
}
