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
package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import me.zane.fairy.api.LiveNetService;
import me.zane.fairy.db.LogcatDao;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class ContentMergeSource extends MergeResource<LogcatContent, String>{
    private static volatile ContentMergeSource instance;
    private final LogcatDao logcatDao;
    private LiveNetService service;
    private int id = -1;
    private StringBuilder sb;

    public ContentMergeSource(AppExecutors executors, LogcatDao logcatDao, LiveNetService service) {
        super(executors);
        this.logcatDao = logcatDao;
        this.service = service;
    }

    public void init(int id) {
        this.id = id;
        sb = new StringBuilder();
    }

    public void replaceDbData() {
        logcatDao.updateLogcatContent(new LogcatContent(id, sb.toString()));
    }

    @Override
    protected void clearSaveData() {
        sb.delete(0, sb.toString().length());
    }

    @Override
    protected void appendResult(String result) {
        if (checkId()) {
            if (!result.equals("")) {
                sb.append(result);
            }
        }
    }

    @Override
    protected LiveData<LogcatContent> loadFromDb() {
        if (checkId()) {
            return logcatDao.queryLogcatContent(id);
        }
        return new MutableLiveData<>();
    }

    @Override
    protected LiveData<ApiResponse<String>> loadFromNet() {
        return service.getData();
    }

    @Override
    protected LogcatContent castNetToLocal(String s) {
        return new LogcatContent(id, s);
    }

    @Override
    protected String castLocalToNet(LogcatContent content) {
        return content.getContent();
    }

    private boolean checkId() {
        return id >= 0;
    }
}
