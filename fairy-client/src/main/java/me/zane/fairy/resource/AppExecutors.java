/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.zane.fairy.resource;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public class AppExecutors {
    private Executor mainExecutor;
    private Executor netIO;
    private Executor diskIO;

    private AppExecutors() {
        mainExecutor = new MainExecutor();
        netIO = Executors.newFixedThreadPool(3);
        diskIO = Executors.newSingleThreadExecutor();
    }

    private static final class SingletonHolder {
        private static final AppExecutors instance = new AppExecutors();
    }

    public static AppExecutors getInstance() {
        return SingletonHolder.instance;
    }

    public Executor getMainExecutor() {
        return mainExecutor;
    }

    public Executor getNetIO() {
        return netIO;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    private static class MainExecutor implements Executor {
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            handler.post(command);
        }
    }
}
