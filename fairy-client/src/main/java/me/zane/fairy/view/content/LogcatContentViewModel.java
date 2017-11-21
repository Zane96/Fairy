package me.zane.fairy.view.content;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import me.zane.fairy.MySharedPre;
import me.zane.fairy.ZLog;
import me.zane.fairy.repository.LogcatContentRepository;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatContentViewModel extends AndroidViewModel{
    private final LogcatContentRepository repository;
    private boolean isStartFetch = false;
    private int id;

    public LogcatContentViewModel(@NonNull Application application, LogcatContentRepository repository) {
        super(application);
        this.repository = repository;
    }

    //--------------------------destory normally----------------
    boolean isStartFetch(int id) {
        this.id = id;
        return MySharedPre.getInstance().getIsStartFetch(id);
    }

    void setStartFetch(int id, boolean startFetch) {
        this.id = id;
        MySharedPre.getInstance().putIsStartFetch(id, startFetch);
    }

    LiveData<LogcatContent> getData(int id) {
        return repository.getLogcatContent(id);
    }

    void fetch(String options, String filter) {
        repository.fetchData(options, filter);
    }

    void stopFetch() {
        repository.stopFetch();
    }

    void insertContent(LogcatContent content) {
        repository.insertContent(content);
    }

    void insertIfNotExits(LogcatContent content) {
        repository.insertIfNotExits(content);
    }

    /**
     * as same as onDestory()
     */
    @Override
    protected void onCleared() {
        setStartFetch(id, isStartFetch);
        stopFetch();
    }
}
