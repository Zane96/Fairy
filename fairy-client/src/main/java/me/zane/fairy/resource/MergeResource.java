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
    private MediatorLiveData<LocalType> result;

    public MergeResource(AppExecutors executors) {
        this.executors = executors;
    }

    /**
     * Resource is singlton, but
     */
    @MainThread
    public void initData() {
        result = new MediatorLiveData<>();
        executors.getDiskIO().execute(() -> {
            LiveData<LocalType> dbSource = loadFromDb();
            executors.getMainExecutor().execute(() -> {
                result.addSource(dbSource, dbData -> {
                    result.removeSource(dbSource);
                    setValue(dbData);
                    appendResult(castLocalToNet(dbData));
                });
            });
        });
    }

    @MainThread
    public void fetchFromNet() {
        result.addSource(loadFromNet(), netData -> {
            setValue(castNetToLocal(netData.getBody()));
            appendResult(netData.getBody());
        });
    }

    @MainThread
    private void setValue(LocalType newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    @MainThread
    public void stopFetch() {
        result.removeSource(loadFromNet());
    }

    @MainThread
    public LiveData<LocalType> asLiveData() {
        return result;
    }

    /**
     * if you only want load data from local/net(cache), you can return false/true dirctly
     * You can judge the data from Local DB to fetch or not fetch the data from Net
     * @param data
     * @return
     */
    @MainThread
    public abstract boolean shouldFetch(LocalType data);

    /**
     * append the data in StringBuilder
     * @param result
     */
    @WorkerThread
    public abstract void appendResult(NetType result);

    /**
     * load the data from db
     * @return
     */
    @WorkerThread
    public abstract LiveData<LocalType> loadFromDb();

    /**
     * load the data from net
     * @return
     */
    @WorkerThread
    public abstract LiveData<ApiResponse<NetType>> loadFromNet();

    /**
     * @param netType
     * @return
     */
    @MainThread
    public abstract LocalType castNetToLocal(NetType netType);

    @MainThread
    public abstract NetType castLocalToNet(LocalType localType);
}
