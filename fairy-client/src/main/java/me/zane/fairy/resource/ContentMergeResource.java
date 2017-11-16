package me.zane.fairy.resource;

import android.arch.lifecycle.MediatorLiveData;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public class ContentMergeResource implements Resource{
    private AppExecutors executors;
    private final MediatorLiveData<String> contentData;
    private static volatile ContentMergeResource instance;

    private ContentMergeResource(AppExecutors executors) {
        contentData = new MediatorLiveData<>();
    }

    public static ContentMergeResource getInstance(AppExecutors executors) {
        if (instance != null) {
            synchronized (ContentMergeResource.class) {
                if (instance != null) {
                    return new ContentMergeResource(executors);
                }
            }
        }
        return instance;
    }
}
