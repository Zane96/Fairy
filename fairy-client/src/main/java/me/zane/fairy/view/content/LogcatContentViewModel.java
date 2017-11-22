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
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import me.zane.fairy.MySharedPre;
import me.zane.fairy.ZLog;
import me.zane.fairy.databinding.ActivityLogcatBinding;
import me.zane.fairy.repository.LogcatContentRepository;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatContentViewModel extends AndroidViewModel{
    private final LogcatContentRepository repository;
    private int id;
    private ActivityLogcatBinding binding;

    public final ObservableField<String> filter = new ObservableField<>();
    public final ObservableField<String> options = new ObservableField<>();
    public final ObservableField<Boolean> isStartFetch = new ObservableField<>();

    public LogcatContentViewModel(@NonNull Application application, LogcatContentRepository repository) {
        super(application);
        this.repository = repository;
    }

    //---------------------------------action binding---------------------------------
    void setBinding(ActivityLogcatBinding binding) {
        this.binding = binding;
    }

    public void onOptionsChanged(CharSequence s) {
        options.set(s.toString());
    }

    public void onFilterChanged(CharSequence s) {
        filter.set(s.toString());
    }

    public void onStartFetch() {
        isStartFetch.set(true);
        repository.fetchData(options.get(), filter.get());
    }

    public void onStopFetch() {
        isStartFetch.set(false);
        repository.stopFetch();
    }

    //---------------------------------toView------------------------------------
    boolean isStartFetch(int id) {
        this.id = id;
        return MySharedPre.getInstance().getIsStartFetch(id);
    }

    void setStartFetch(int id, boolean startFetch) {
        this.id = id;
        isStartFetch.set(startFetch);
        MySharedPre.getInstance().putIsStartFetch(id, startFetch);
    }

    LiveData<LogcatContent> getData(int id) {
        return repository.getLogcatContent(id);
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
     * as same as onDestory(), but it won't be trigger when destory abnormal
     */
    @Override
    protected void onCleared() {
        repository.stopFetch();
        MySharedPre.getInstance().putIsStartFetch(id, false);
    }
}
