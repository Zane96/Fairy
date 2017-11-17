package me.zane.fairy.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import me.zane.fairy.repository.LogcatItemRepository;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class LogcatItemViewModel extends AndroidViewModel{
    public LogcatItemViewModel(@NonNull Application application, LogcatItemRepository repository) {
        super(application);
    }
}
