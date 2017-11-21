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

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import me.zane.fairy.MySharedPre;
import me.zane.fairy.repository.LogcatContentRepository;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatContentViewModel extends AndroidViewModel{
    private final LogcatContentRepository repository;
    private boolean isStartFetch = false;
    private int id;

    public LogcatContentViewModel(@NonNull Application application, LogcatContentRepository repository) {
        super(application);
        this.repository = repository;
    }

    //--------------------------destory normally----------------
    boolean isStartFetch(int id) {
        this.id = id;
        return MySharedPre.getInstance().getIsStartFetch(id);
    }

    void setStartFetch(int id, boolean startFetch) {
        this.id = id;
        MySharedPre.getInstance().putIsStartFetch(id, startFetch);
    }

    LiveData<LogcatContent> getData(int id) {
        return repository.getLogcatContent(id);
    }

    void fetch(String options, String filter) {
        repository.fetchData(options, filter);
    }

    void stopFetch() {
        repository.stopFetch();
    }

    void insertContent(LogcatContent content) {
        repository.insertContent(content);
    }

    void insertIfNotExits(LogcatContent content) {
        repository.insertIfNotExits(content);
    }

    void clearContent(LogcatContent content) {
        repository.clearContent(content);
    }

    /**
     * as same as onDestory()
     */
    @Override
    protected void onCleared() {
        setStartFetch(id, isStartFetch);
        stopFetch();
    }
}
