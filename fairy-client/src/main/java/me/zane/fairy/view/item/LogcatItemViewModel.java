package me.zane.fairy.view.item;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.zane.fairy.repository.LogcatItemRepository;
import me.zane.fairy.vo.LogcatItem;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatItemViewModel extends AndroidViewModel{
    private final LogcatItemRepository repository;

    public LogcatItemViewModel(@NonNull Application application, LogcatItemRepository repository) {
        super(application);
        this.repository = repository;
    }

    public LiveData<List<LogcatItem>> queryItem() {
        return repository.queryLocatItem();
    }

    public void insertItem(LogcatItem item) {
        repository.insertLogcatItem(item);
    }

    public void updateItem(LogcatItem item) {
        repository.updateLogcatItem(item);
    }

    public void deleteItem(LogcatItem item) {
        repository.deleteLogcatItem(item);
    }
}
