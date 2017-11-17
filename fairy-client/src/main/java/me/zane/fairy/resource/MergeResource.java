package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import java.util.Objects;

/**
 * merge both of net and local source
 * make local source to be the single data provider
 *
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public abstract class MergeResource<LocalType, NetType> {
    private final AppExecutors executors;
    private final MediatorLiveData<LocalType> result = new MediatorLiveData<>();

    public MergeResource(AppExecutors executors) {
        this.executors = executors;
        initData();
    }

    @MainThread
    private void setValue(LocalType newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void initData() {
        LiveData<LocalType> dbSource = loadFromDb();
        result.addSource(dbSource, dbData -> {
            if (dbData != null) {
                setValue(dbData);
            }
            //remove first or it will throw exception when add different observer in same source
            result.removeSource(dbSource);
            if (shouldFetch(dbData)) {
                fetchFromNet(true);
            } else {
                result.addSource(dbSource, this::setValue);
            }
        });
    }

    public void fetchFromNet(boolean isInit) {
        LiveData<ApiResponse<NetType>> netSource = loadFromNet();
        result.addSource(netSource, netData -> {
            if (isInit) {
                result.removeSource(netSource);
            }
            //load in db
            executors.getDiskIO().execute(() -> {
                saveInLocal(netData.getBody());
                executors.getMainExecutor().execute(() -> result.addSource(loadFromDb(), this::setValue));
            });
//            if (netData.isSuccussful()) {
//                //load in db
//                executors.getDiskIO().execute(() -> {
//                    saveInLocal(netData.getBody());
//                    executors.getMainExecutor().execute(() -> result.addSource(loadFromDb(), result::setValue));
//                });
//            } else {
//                result.addSource(dbSource, newData -> result.setValue(newData));
//            }
        });
    }

    public void stopFetchFromNet() {
        LiveData<ApiResponse<NetType>> netSource = loadFromNet();
        result.removeSource(netSource);
    }

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
     * updata/insert the data in the local db
     * @param result
     */
    @WorkerThread
    public abstract void saveInLocal(NetType result);

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
}
