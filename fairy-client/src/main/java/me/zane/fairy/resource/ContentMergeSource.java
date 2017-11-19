package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import me.zane.fairy.ZLog;
import me.zane.fairy.api.ContentNetService;
import me.zane.fairy.vo.LogcatContent;
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

    private ContentMergeSource(AppExecutors executors, LogcatDao logcatDao) {
        super(executors);
        this.logcatDao = logcatDao;
    }

    public static ContentMergeSource getInstance(AppExecutors executors, LogcatDao logcatDao) {
        if (instance == null) {
            synchronized (ContentMergeSource.class) {
                if (instance == null) {
                    instance = new ContentMergeSource(executors, logcatDao);
                }
            }
        }
        return instance;
    }

    public void setService(ContentNetService service) {
        this.service = service;
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
        return new MutableLiveData<>();
    }

    @Override
    public LiveData<ApiResponse<String>> loadFromNet() {
        return service.getData();
    }

    @Override
    public LogcatContent castNetToLocal(String s) {
        return new LogcatContent(-1, s);
    }

    public void insertLogcatContent(LogcatContent content) {
        logcatDao.insertLogcatContent(content);
    }

    public void insertIfNotExits(LogcatContent content) {
        logcatDao.insertIfNotExits(content);
    }

    private boolean checkId() {
        return id >= 0;
    }
}
