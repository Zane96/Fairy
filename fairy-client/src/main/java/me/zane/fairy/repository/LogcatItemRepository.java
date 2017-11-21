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
package me.zane.fairy.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.zane.fairy.db.LogcatDao;
import me.zane.fairy.db.LogcatDatabase;
import me.zane.fairy.vo.LogcatItem;
import me.zane.fairy.resource.AppExecutors;

/**
 * only load the data from db, so we have no need to depend MergeResource
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public class LogcatItemRepository {
    private LogcatDao logcatDao;
    private AppExecutors executors;
    private static volatile LogcatItemRepository instance;

    private LogcatItemRepository(AppExecutors executors) {
        this.logcatDao = LogcatDatabase.getInstance().logcatDao();
        this.executors = executors;
    }

    public static LogcatItemRepository getInstance(@NonNull AppExecutors executors) {
        if (instance == null) {
            synchronized (LogcatItemRepository.class) {
                if (instance == null) {
                    instance = new LogcatItemRepository(executors);
                }
            }
        }
        return instance;
    }

    //----------------------------Dao-----------------------------

    public LiveData<List<LogcatItem>> queryLocatItem() {
        return logcatDao.queryLogcatItem();
    }

    public void insertLogcatItem(LogcatItem item) {
        executors.getDiskIO().execute(() -> logcatDao.insertLogcatItem(item));
    }

    public void deleteLogcatItem(LogcatItem item) {
        executors.getDiskIO().execute(() -> logcatDao.deleteLogcatItem(item));
    }

    public void updateLogcatItem(LogcatItem item) {
        executors.getDiskIO().execute(() -> logcatDao.updateLogcatItem(item));
    }
}
