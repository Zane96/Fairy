package me.zane.fairy.repository;

import me.zane.fairy.resource.AppExecutors;

/**
 * inject the excutors for Repository
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class RepositoryInjection {
    private final AppExecutors executors;

    private RepositoryInjection() {
        executors = AppExecutors.getInstance();
    }

    private static final class SingletonHolder {
        private static final RepositoryInjection instance = new RepositoryInjection();
    }

    public static RepositoryInjection getInstance() {
        return SingletonHolder.instance;
    }

    //---------------------------------------------------------------------------------------

    public LogcatContentRepository provideContentRepo() {
        return LogcatContentRepository.getInstance(executors);
    }

    public LogcatItemRepository provideItemRepo() {
        return LogcatItemRepository.getInstance(executors);
    }
}
