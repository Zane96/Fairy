package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;

import me.zane.fairy.api.ContentNetService;
import me.zane.fairy.db.LogcatContent;
import me.zane.fairy.db.LogcatDao;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class ContentMergeSource extends MergeResource<LogcatContent, String>{
    private static volatile ContentMergeSource instance;
    private LogcatDao logcatDao;
    private ContentNetService service;
    private int id = -1;

    private ContentMergeSource(AppExecutors executors, LogcatDao logcatDao, ContentNetService service) {
        super(executors);
        this.logcatDao = logcatDao;
        this.service = service;
    }

    public static ContentMergeSource getInstance(AppExecutors executors, LogcatDao logcatDao, ContentNetService service) {
        if (instance != null) {
            synchronized (ContentMergeSource.class) {
                if (instance != null) {
                    instance = new ContentMergeSource(executors, logcatDao, service);
                }
            }
        }
        return instance;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean shouldFetch(LogcatContent data) {
        return true;
    }

    @Override
    public void saveInLocal(String result) {
        if (checkId()) {
            LogcatContent content = new LogcatContent(id, result);
            logcatDao.updateLogcatContent(content);
        }
    }

    @Override
    public LiveData<LogcatContent> loadFromDb() {
        if (checkId()) {
            return logcatDao.queryLogcatContent(id);
        }
        return null;
    }

    @Override
    public LiveData<ApiResponse<String>> loadFromNet() {
        return service.getData();
    }

    private boolean checkId() {
        return id >= 0;
    }
}
