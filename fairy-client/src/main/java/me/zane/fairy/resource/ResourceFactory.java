package me.zane.fairy.resource;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public class ResourceFactory {
    private static volatile ResourceFactory instance;
    private AppExecutors executors;

    private ResourceFactory(AppExecutors executors) {
        this.executors = executors;
    }

    public static ResourceFactory getInstance(AppExecutors executors) {
        if (instance != null) {
            synchronized (ResourceFactory.class) {
                if (instance != null) {
                    return new ResourceFactory(executors);
                }
            }
        }
        return instance;
    }

    public <T extends Resource> T creat(Class<T> type) {
        if (type.isAssignableFrom(ContentMergeResource.class)) {
            return (T) ContentMergeResource.getInstance(executors);
        } else if (type.isAssignableFrom(LogcatItemResource.class)) {
            return (T) LogcatItemResource.getInstance(executors);
        } else {
            throw new IllegalArgumentException("Unknow Resource class: " + type);
        }
    }
}
