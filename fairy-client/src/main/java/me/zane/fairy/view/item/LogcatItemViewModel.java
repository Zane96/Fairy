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

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.zane.fairy.repository.LogcatItemRepository;
import me.zane.fairy.vo.LogcatItem;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatItemViewModel extends AndroidViewModel{
    private final LogcatItemRepository repository;

    public LogcatItemViewModel(@NonNull Application application, LogcatItemRepository repository) {
        super(application);
        this.repository = repository;
    }

    public LiveData<List<LogcatItem>> queryItem() {
        return repository.queryLocatItem();
    }

    public void insertItem(LogcatItem item) {
        repository.insertLogcatItem(item);
    }

    public void updateItem(LogcatItem item) {
        repository.updateLogcatItem(item);
    }

    public void deleteItem(LogcatItem item) {
        repository.deleteLogcatItem(item);
    }
}
