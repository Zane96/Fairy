package me.zane.fairy.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.zane.fairy.db.LogcatDao;
import me.zane.fairy.db.LogcatDatabase;
import me.zane.fairy.db.LogcatItem;
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
        if (instance != null) {
            synchronized (LogcatItemRepository.class) {
                if (instance != null) {
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
