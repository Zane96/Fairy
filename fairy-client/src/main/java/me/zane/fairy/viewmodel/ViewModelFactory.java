package me.zane.fairy.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import me.zane.fairy.App;
import me.zane.fairy.repository.RepositoryInjection;

/**
 * Created by Zane on 2017/11/17.
 * Email: zanebot96@gmail.com
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application application;
    private RepositoryInjection repositoryInjection;

    private ViewModelFactory(){
        application = App.getInstance();
        repositoryInjection = RepositoryInjection.getInstance();
    }

    private static final class SingletonHolder {
        private static final ViewModelFactory instance = new ViewModelFactory();
    }

    public static ViewModelFactory getInstance() {
        return SingletonHolder.instance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LogcatItemViewModel.class)) {
            return (T) new LogcatItemViewModel(application, repositoryInjection.provideItemRepo());
        } else if (modelClass.isAssignableFrom(LogcatContentViewModel.class)) {
            return (T) new LogcatContentViewModel(application, repositoryInjection.provideContentRepo());
        } else {
            throw new IllegalArgumentException("Unkonw viewmodel type: " + modelClass);
        }
    }
}
