package me.zane.fairy.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import me.zane.fairy.ZLog;
import me.zane.fairy.api.ContentNetService;
import me.zane.fairy.vo.LogcatContent;
import me.zane.fairy.db.LogcatDatabase;
import me.zane.fairy.resource.AppExecutors;
import me.zane.fairy.resource.ContentMergeSource;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatContentRepository {
    private static volatile LogcatContentRepository instance;
    private final ContentMergeSource source;
    private static ContentNetService service;
    private final AppExecutors executors;

    private LogcatContentRepository(AppExecutors executors) {
        source = ContentMergeSource.getInstance(executors, LogcatDatabase.getInstance().logcatDao());
        this.executors = executors;
    }

    public static LogcatContentRepository getInstance(@NonNull AppExecutors executors) {
        ZLog.d("new service------------");
        service = new ContentNetService();
        if (instance == null) {
            synchronized (LogcatContentRepository.class) {
                if (instance == null) {
                    instance = new LogcatContentRepository(executors);
                }
            }
        }
        return instance;
    }

    public LiveData<LogcatContent> getLogcatContent(int id) {
        source.init(id, service);
        source.initData();
        return source.asLiveData();
    }

    public void fetchData(String options, String filter) {
        source.fetchFromNet();
        service.enqueue(options, filter);
    }

    public void stopFetch() {
        service.stop();
        source.stopFetch();
        executors.getDiskIO().execute(source::replaceDbData);
    }

    public void insertContent(LogcatContent content) {
        executors.getDiskIO().execute(() -> source.insertLogcatContent(content));
    }

    public void insertIfNotExits(LogcatContent content) {
        executors.getDiskIO().execute(() -> source.insertIfNotExits(content));
    }
}
