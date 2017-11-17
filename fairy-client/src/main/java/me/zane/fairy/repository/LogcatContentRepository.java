package me.zane.fairy.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import me.zane.fairy.api.ContentNetService;
import me.zane.fairy.db.LogcatContent;
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

    private LogcatContentRepository(AppExecutors executors) {
        source = ContentMergeSource.getInstance(executors, LogcatDatabase.getInstance().logcatDao(), service);
    }

    public static LogcatContentRepository getInstance(@NonNull AppExecutors executors) {
        service = new ContentNetService();
        if (instance != null) {
            synchronized (LogcatContentRepository.class) {
                if (instance != null) {
                    instance = new LogcatContentRepository(executors);
                }
            }
        }
        return instance;
    }

    public LiveData<LogcatContent> getLogcatContent(int id) {
        source.setId(id);
        return source.asLiveData();
    }

    public void fetchData(int id) {
        source.setId(id);
        source.fetchData();
    }
}
