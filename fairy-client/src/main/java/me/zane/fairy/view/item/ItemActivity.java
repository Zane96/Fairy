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
    private static final String NULL_VALUE = "command_null";
    private static final int NULL_POSITION = -1;//不用刷新item

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
            ZLog.d(items + " items");
            adapter.addAll(items);
        });

        itemTouchCallback.getRemoveObservable().subscribe(position -> viewModel.deleteItem(adapter.get(position)));
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
            Intent intent = new Intent(ItemActivity.this, LogcatActivity.class);
            LogcatItem item = adapter.get(position);
            intent.putExtra(LogcatActivity.OPTIONS, item.getOptions());
            intent.putExtra(LogcatActivity.INDEX_KEY, item.getId());
            intent.putExtra(LogcatActivity.FILTER, item.getFilter());
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_main_bar:
                viewModel.insertItem(new LogcatItem(0, "", ""));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
