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
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import me.zane.fairy.Config;
import me.zane.fairy.R;
import me.zane.fairy.ZLog;
import me.zane.fairy.databinding.ActivityLogcatBinding;
import me.zane.fairy.view.item.ItemActivity;
import me.zane.fairy.view.item.LogcatItemViewModel;
import me.zane.fairy.viewmodel.ViewModelFactory;
import me.zane.fairy.vo.LogcatContent;
import me.zane.fairy.vo.LogcatItem;


/**
 * Created by Zane on 2017/10/29.
 * Email: zanebot96@gmail.com
 */

public class LogcatActivity extends AppCompatActivity{
    public static final int CONTENT_RESULT_CODE = 321;
    public static final String LOGCAT_ITEM = "logcat_item";

    private ActivityLogcatBinding binding;
    private LogcatContentViewModel viewModel;
    private IWindowControl windowControl;

    private String options;
    private String filter;
    private String grep;
    private int id;

    private boolean isFirstLoad = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logcat);

        LogcatItem item = getIntent().getParcelableExtra(LOGCAT_ITEM);
        id = item.getId();
        options = item.getOptions();
        filter = item.getFilter();
        grep = item.getGrep();

        init();
    }

    private void init() {
        //绑定
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(LogcatContentViewModel.class);
        binding.setModel(viewModel);
        viewModel.init(id, grep, binding);

        viewModel.onFilterChanged(filter);
        viewModel.onOptionsChanged(options);
        viewModel.onGrepChanged(grep);

        windowControl = new WindowControl(this);
        windowControl.initData(viewModel.getData());

        binding.scrollviewLogcat.setSmoothScrollingEnabled(true);
        registerData();
    }

    public void gloablWindowReact(View view) {
        Button btnGloab = binding.btnGloba;
        if (btnGloab.getText().equals(getResources().getString(R.string.open_globa_window))) {
            btnGloab.setText(getResources().getText(R.string.close_globa_window));
            viewModel.getData().removeObservers(this);
            windowControl.open();
        } else {
            btnGloab.setText(getResources().getText(R.string.open_globa_window));
            registerData();
            windowControl.close();
        }
    }

    private void registerData() {
        viewModel.getData().observe(this, content -> {
            content.setFirst(isFirstLoad);
            isFirstLoad = false;
            binding.setLogcatContent(content);
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

    @Override
    protected void onStop() {
        super.onStop();
        LogcatItemViewModel itemViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(LogcatItemViewModel.class);
        itemViewModel.updateItem(new LogcatItem(id, binding.getModel().options.get(), binding.getModel().filter.get(), binding.getModel().grep.get()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        LogcatItem item = new LogcatItem(id, viewModel.options.get(), viewModel.filter.get(), viewModel.grep.get());
        intent.putExtra(LOGCAT_ITEM, item);
        setResult(CONTENT_RESULT_CODE, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        windowControl.close();
    }
}
