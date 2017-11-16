package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.zane.fairy.db.LogcatContent;
import me.zane.fairy.db.LogcatDao;
import me.zane.fairy.db.LogcatDatabase;
import me.zane.fairy.db.LogcatItem;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public class LogcatItemResource implements Resource{
    private LogcatDao logcatDao;
    private AppExecutors executors;
    private final MutableLiveData<List<LogcatItem>> itemData;
    private final MutableLiveData<LogcatContent> contentData;
    private static volatile LogcatItemResource instance;

    private LogcatItemResource(AppExecutors executors) {
        itemData = new MutableLiveData<>();
        contentData = new MutableLiveData<>();
        this.logcatDao = LogcatDatabase.getInstance().logcatDao();
        this.executors = executors;
    }

    public static LogcatItemResource getInstance(@NonNull AppExecutors executors) {
        if (instance != null) {
            synchronized (LogcatItemResource.class) {
                if (instance != null) {
                    instance = new LogcatItemResource(executors);
                }
            }
        }
        return instance;
    }

    public LiveData<List<LogcatItem>> getItemData() {
        return itemData;
    }

    public LiveData<LogcatContent> getContentData() {
        return contentData;
    }

    //----------------------------Dao-----------------------------

    public void queryLocatItem() {
        executors.getDiskIO().execute(() -> {
            final List<LogcatItem> items = logcatDao.queryLogcatItem();
            itemData.postValue(items);
        });
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
