package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import java.util.Objects;

import me.zane.fairy.ZLog;
import me.zane.fairy.vo.LogcatContent;

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
    }

    @MainThread
    private void setValue(LocalType newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    public void initData() {
        LiveData<LocalType> dbSource = loadFromDb();
        ZLog.d(loadFromNet() + " net1");
        ZLog.d(loadFromNet() + " net2");
        result.removeSource(loadFromNet());
        result.addSource(dbSource, dbData -> {
            ZLog.d("db 1----------------" + dbData.toString());
            if (dbData != null) {
                setValue(dbData);
            }
            //remove first or it will throw exception when add different observer in a same source
            result.removeSource(dbSource);
            if (shouldFetch(dbData)) {
                fetchFromNet();
            } else {
                result.addSource(dbSource, this::setValue);
            }
        });
    }

    private void fetchFromNet() {
        LiveData<ApiResponse<NetType>> netSource = loadFromNet();
        result.addSource(netSource, netData -> {
            ZLog.d("net 1--------------" + netData.getBody().toString());
            if (netData == null) {
                return;
            }
            //load in db
            executors.getDiskIO().execute(() -> {
                if (netData.isSuccussful()) {
                    ZLog.d("save in db-----------------");
                    saveInLocal(netData.getBody());
                    executors.getMainExecutor().execute(() -> {
                        LiveData<LocalType> dbSource = loadFromDb();
                        result.addSource(dbSource, newData -> {
                            ZLog.d("db 2----------------" + newData.toString());
                            setValue(newData);
                            result.removeSource(dbSource);
                        });
                    });
                } else {
                    result.postValue(castNetToLocal(netData.getBody()));
                }
            });
        });
    }

    public void stopFetchFromNet() {
        result.removeSource(loadFromNet());
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

    /**
     * Only when net is not successful will use this method to cast NetType to LocalType
     * Because it will return Throwable's message instand of real data. So we have no need to
     * save error message in the Local db.
     * @param netType
     * @return
     */
    @MainThread
    public abstract LocalType castNetToLocal(NetType netType);
}
