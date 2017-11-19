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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;

import me.zane.fairy.R;
import me.zane.fairy.ZLog;
import me.zane.fairy.databinding.ActivityLogcatBinding;
import me.zane.fairy.view.item.LogcatItemViewModel;
import me.zane.fairy.viewmodel.ViewModelFactory;
import me.zane.fairy.vo.LogcatContent;
import me.zane.fairy.vo.LogcatItem;


/**
 * Created by Zane on 2017/10/29.
 * Email: zanebot96@gmail.com
 */

public class LogcatActivity extends AppCompatActivity{
    public static final String INDEX_KEY = "index_key";
    public static final String OPTIONS = "options";
    public static final String FILTER = "filter";

    private ActivityLogcatBinding binding;
    private LogcatContentViewModel viewModel;

    private String options;
    private String filter;
    private int id;
    private LogcatItem logcatItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logcat);

        id = getIntent().getIntExtra(INDEX_KEY, -1);
        options = getIntent().getStringExtra(OPTIONS);
        filter = getIntent().getStringExtra(FILTER);
        logcatItem = new LogcatItem(id, options, filter);

        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(LogcatContentViewModel.class);
        viewModel.insertIfNotExits(new LogcatContent(id, "fairy init"));
        viewModel.getData(id).observe(this, content -> {
            binding.setLogcatContent(content);
            binding.scrollviewLogcat.smoothScrollTo(0, binding.textDataLogcat.getHeight());
            ZLog.d(content.getContent());
        });

        initView();
    }

    private void initView() {
        if (!options.equals("")) {
            binding.editOptionsLogcat.setHint(options);
        }

        if (!filter.equals("")) {
            binding.editFilterLogcat.setHint(filter);
        }

        binding.btnStartLogcat.setOnClickListener(v -> {
            binding.btnStartLogcat.setEnabled(false);
            binding.btnStopLogcat.setEnabled(true);
            viewModel.fetch(id, options, filter);
        });

        binding.btnStopLogcat.setEnabled(false);
        binding.btnStopLogcat.setOnClickListener(v -> {
            binding.btnStartLogcat.setEnabled(true);
            binding.btnStopLogcat.setEnabled(false);
            viewModel.stopFetch();
        });

        LogcatItemViewModel itemViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(LogcatItemViewModel.class);
        binding.editOptionsLogcat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                options = s.toString();
                logcatItem.setOptions(options);
                itemViewModel.updateItem(logcatItem);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.editFilterLogcat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter = s.toString();
                logcatItem.setFilter(filter);
                itemViewModel.updateItem(logcatItem);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
