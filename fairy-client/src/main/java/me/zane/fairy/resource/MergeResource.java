package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public abstract class MergeResource<LocalType, NetType> {

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
    public abstract LiveData<NetType> loadFromNet();
}
