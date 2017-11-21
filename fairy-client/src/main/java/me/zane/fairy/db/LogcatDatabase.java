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
package me.zane.fairy.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import me.zane.fairy.App;
import me.zane.fairy.vo.LogcatContent;
import me.zane.fairy.vo.LogcatItem;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

@Database(entities = {LogcatItem.class, LogcatContent.class}, version = 2)
public abstract class LogcatDatabase extends RoomDatabase{
    private static final class SingletonHolder {
        private static final LogcatDatabase instance = Room.databaseBuilder(App.getInstance(),
                                                                    LogcatDatabase.class,
                                                                    "Logcat.db").fallbackToDestructiveMigration().build();
    }

    public static LogcatDatabase getInstance() {
        return SingletonHolder.instance;
    }

    public abstract LogcatDao logcatDao();
}
