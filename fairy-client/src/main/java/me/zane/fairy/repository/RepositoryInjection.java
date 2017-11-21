/*
 * Copyright (C) 2017 Zane.
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
