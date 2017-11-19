package me.zane.fairy.view.content;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import me.zane.fairy.ZLog;
import me.zane.fairy.repository.LogcatContentRepository;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatContentViewModel extends AndroidViewModel{
    private final LogcatContentRepository repository;

    public LogcatContentViewModel(@NonNull Application application, LogcatContentRepository repository) {
        super(application);
        this.repository = repository;
    }

    public LiveData<LogcatContent> getData(int id) {
        return repository.getLogcatContent(id);
    }

    public void fetch(int id, String options, String filter) {
        repository.fetchData(id, options, filter);
    }

    public void stopFetch() {
        repository.stopFetch();
    }

    public void insertContent(LogcatContent content) {
        repository.insertContent(content);
    }

    public void insertIfNotExits(LogcatContent content) {
        repository.insertIfNotExits(content);
    }
}
