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
import android.arch.persistence.room.InvalidationTracker;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Set;

import me.zane.fairy.Config;
import me.zane.fairy.MySharedPre;
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

    private boolean isStartFetch = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logcat);

        id = getIntent().getIntExtra(INDEX_KEY, -1);
        options = getIntent().getStringExtra(OPTIONS);
        filter = getIntent().getStringExtra(FILTER);
        logcatItem = new LogcatItem(id, options, filter);

        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(LogcatContentViewModel.class);

        viewModel.insertIfNotExits(new LogcatContent(id, "init fairy"));
        viewModel.getData(id).observe(this, content -> {
            ZLog.d("obse: " + content.toString());
            binding.setLogcatContent(content);
            new Handler().postDelayed(() -> binding.scrollviewLogcat.smoothScrollTo(0, binding.textDataLogcat.getHeight()), 100);
        });

        isStartFetch = viewModel.isStartFetch(id);
        initView();
    }

    private void initView() {
        binding.setOptions(options);
        binding.setFilter(filter);
        binding.setIsStartFetch(isStartFetch);

        binding.btnStartLogcat.setOnClickListener(v -> {
            isStartFetch = true;
            binding.setIsStartFetch(true);
            viewModel.setStartFetch(id, true);
            viewModel.fetch(options, filter);
        });

        binding.btnStopLogcat.setOnClickListener(v -> {
            isStartFetch = false;
            binding.setIsStartFetch(false);
            viewModel.setStartFetch(id, false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logcat_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear_logcat_bar:
                viewModel.clearContent(new LogcatContent(id, Config.CLEAR_SIGNAL));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
