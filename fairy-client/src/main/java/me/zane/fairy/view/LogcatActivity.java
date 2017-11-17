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
package me.zane.fairy.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import me.zane.fairy.MySharedPre;
import me.zane.fairy.R;
import me.zane.fairy.api.ContentNetService;


/**
 * Created by Zane on 2017/10/29.
 * Email: zanebot96@gmail.com
 */

public class LogcatActivity extends AppCompatActivity{
    public static final String INDEX_KEY = "index_key";
    private static final int DEFAULT_INDEX = -1;
    private EditText mOptionsEdit;
    private EditText mFilterEdit;
    private Button mStartBtn;
    private Button mStopBtn;
    private TextView mDataText;
    private ScrollView mScrollView;

    private int index;
    private String options = "";
    private String filter = "";

    private ContentNetService engine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcat);
        index = getIntent().getIntExtra(INDEX_KEY, DEFAULT_INDEX);
        if (index == DEFAULT_INDEX) {
            throw new IllegalArgumentException("index of item can't be null");
        }

        options = MySharedPre.getInstance().getOptions(index, "");
        filter = MySharedPre.getInstance().getFilter(index, "");
        engine = new ContentNetService();

        mOptionsEdit = findViewById(R.id.edit_options_logcat);
        mFilterEdit = findViewById(R.id.edit_filter_logcat);
        mStartBtn = findViewById(R.id.btn_start_logcat);
        mStopBtn = findViewById(R.id.btn_stop_logcat);
        mDataText = findViewById(R.id.text_data_logcat);
        mScrollView = findViewById(R.id.scrollview_logcat);

        mStartBtn.setOnClickListener(v -> {
            mStartBtn.setEnabled(false);
            mStopBtn.setEnabled(true);
//            engine.enqueue(options, filter, new ContentNetService.DataCallBack() {
//                @Override
//                public void onSuccess(LogcatData date) {
//                    mDataText.append(Html.fromHtml(date.getData()));
//                    mScrollView.smoothScrollTo(0, mDataText.getHeight());
//                }
//
//                @Override
//                public void onFailed(String error) {
//                    mDataText.append(error);
//                }
//            });
        });

        mStopBtn.setEnabled(false);
        mStopBtn.setOnClickListener(v -> {
            mStartBtn.setEnabled(true);
            mStopBtn.setEnabled(false);
            engine.stop();
        });

        if (!options.equals("")) {
            mOptionsEdit.setHint(options);
        }
        mOptionsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                options = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (!filter.equals("")) {
            mFilterEdit.setHint(filter);
        }
        mFilterEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MySharedPre.getInstance().putOptions(index, options);
        MySharedPre.getInstance().putFilter(index, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        engine.stop();
    }
}
