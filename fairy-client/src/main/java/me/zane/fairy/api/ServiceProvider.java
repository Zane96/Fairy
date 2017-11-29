package me.zane.fairy.api;

/**
 * Created by Zane on 2017/11/28.
 * Email: zanebot96@gmail.com
 */

public class ServiceProvider {

    public static LiveNetService provideLiveService() {
        return new LiveNetService();
    }

    public static CallbackNetService provideCallbackService() {
        return new CallbackNetService();
    }
}
