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
package me.zane.fairy.view.item;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import me.zane.fairy.R;
import me.zane.fairy.ZLog;
import me.zane.fairy.view.content.LogcatActivity;
import me.zane.fairy.viewmodel.ViewModelFactory;
import me.zane.fairy.vo.LogcatItem;


public class ItemActivity extends AppCompatActivity {
    public static final int CONTENT_RESULT_CODE = 321;
    private MyAdapter adapter;
    private LiveData<List<LogcatItem>> observer;
    private LogcatItemViewModel viewModel;
    private MyItemTouchCallback itemTouchCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();

        observer.observe(this, items -> {
            adapter.addAll(items);
            observer.removeObservers(ItemActivity.this);
        });

        itemTouchCallback.getRemoveObservable().subscribe(position -> {
            viewModel.deleteItem(adapter.get(position));
            adapter.remove(position);
        });
    }

    private void init() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(LogcatItemViewModel.class);
        observer = viewModel.queryItem();
    }

    private void initView() {
        RecyclerView recycleView = findViewById(R.id.recycle_main);
        adapter = new MyAdapter(this);
        itemTouchCallback = new MyItemTouchCallback(this,
                                                           adapter,
                                                           ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                                                           ItemTouchHelper.LEFT | ItemTouchHelper.END);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);

        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recycleView.setAdapter(adapter);
        adapter.addAll(new ArrayList<>());
        itemTouchHelper.attachToRecyclerView(recycleView);

        adapter.setOnClickListener(position -> {
            viewModel.setTempPosition(position);
            Intent intent = new Intent(ItemActivity.this, LogcatActivity.class);
            LogcatItem item = adapter.get(position);
            intent.putExtra(LogcatActivity.LOGCAT_ITEM, item);
            startActivityForResult(intent, LogcatActivity.ITEM_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case ItemActivity.CONTENT_RESULT_CODE:
                LogcatItem item = data.getParcelableExtra(LogcatActivity.LOGCAT_ITEM);
                adapter.replace(viewModel.getTempPosition(), item);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_main_bar:
                int lastId = viewModel.getLastId() + 1;
                LogcatItem item = LogcatItem.creatEmpty(lastId);
                viewModel.insertItem(item);
                viewModel.putLastId(lastId);
                adapter.add(item);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
