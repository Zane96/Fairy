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
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import java.util.Objects;

import me.zane.fairy.ZLog;

/**
 * merge both of net and local source
 * make local source to be the single data provider
 *
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public abstract class MergeResource<LocalType, NetType> {
    private final AppExecutors executors;
    private final MediatorLiveData<LocalType> result;

    MergeResource(AppExecutors executors) {
        this.executors = executors;
        result = new MediatorLiveData<>();
    }

    @MainThread
    public void initData() {
        executors.getDiskIO().execute(() -> {
            LiveData<LocalType> dbSource = loadFromDb();
            executors.getMainExecutor().execute(() -> {
                result.addSource(dbSource, dbData -> {
                    result.removeSource(dbSource);
                    if (dbData != null) {
                        ZLog.d("db------" + dbData.toString());
                        setValue(dbData);
                        appendResult(castLocalToNet(dbData));
                    }
                });
            });
        });
    }

    @MainThread
    public void fetchFromNet() {
        result.addSource(loadFromNet(), netData -> {
            ZLog.d("net----------" + netData.getBody().toString());
            setValue(castNetToLocal(netData.getBody()));
            appendResult(netData.getBody());
        });
    }

    @MainThread
    public void stopFetchFromNet() {
        result.removeSource(loadFromNet());
    }

    @MainThread
    public void clearContent() {
        //observe data
        executors.getDiskIO().execute(() -> {
            LiveData<LocalType> dbSource = loadFromDb();
            executors.getMainExecutor().execute(() -> {
                result.addSource(dbSource, dbData -> {
                    clearSaveData();
                    result.removeSource(dbSource);
                    setValue(dbData);
                });
            });
        });
    }

    @MainThread
    private void setValue(LocalType newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    @MainThread
    public LiveData<LocalType> asLiveData() {
        return result;
    }

    /**
     * clear the save data in StringBuilder
     */
    @MainThread
    protected abstract void clearSaveData();

    /**
     * append the data in StringBuilder
     * @param result
     */
    @WorkerThread
    protected abstract void appendResult(NetType result);

    /**
     * load the data from db
     * @return
     */
    @WorkerThread
    protected abstract LiveData<LocalType> loadFromDb();

    /**
     * get the livedata from net
     * @return
     */
    @MainThread
    protected abstract LiveData<ApiResponse<NetType>> loadFromNet();

    /**
     * @param netType
     * @return
     */
    @MainThread
    protected abstract LocalType castNetToLocal(NetType netType);

    @MainThread
    protected abstract NetType castLocalToNet(LocalType localType);
}
