package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import me.zane.fairy.api.ContentNetService;
import me.zane.fairy.db.LogcatDao;
import me.zane.fairy.vo.LogcatContent;

/**
 * 可以做成单列的，插拔id, service就行了
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class ContentMergeSource extends MergeResource<LogcatContent, String>{
    private static volatile ContentMergeSource instance;
    private final LogcatDao logcatDao;
    private ContentNetService service;
    private int id = -1;
    private StringBuilder sb;

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

    public void init(int id, ContentNetService service) {
        this.id = id;
        this.service = service;
        sb = new StringBuilder();
    }

    public void replaceDbData() {
        logcatDao.updateLogcatContent(new LogcatContent(id, sb.toString()));
    }

    @Override
    public boolean shouldFetch(LogcatContent data) {
        return true;
    }

    @Override
    public void appendResult(String result) {
        if (checkId()) {
            if (!result.equals("")) {
                sb.append(result);
            }
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
        return new LogcatContent(id, s);
    }

    @Override
    public String castLocalToNet(LogcatContent content) {
        return content.getContent();
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
