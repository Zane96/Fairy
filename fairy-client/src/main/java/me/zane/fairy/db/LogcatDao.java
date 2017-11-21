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

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import me.zane.fairy.vo.LogcatContent;
import me.zane.fairy.vo.LogcatItem;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

@Dao
public interface LogcatDao {

    //----------------LogcatItem--------------
    @Query("SELECT * FROM " + LogcatItem.TABLE_NAME)
    LiveData<List<LogcatItem>> queryLogcatItem();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLogcatItem(LogcatItem item);

    @Delete
    void deleteLogcatItem(LogcatItem item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateLogcatItem(LogcatItem item);

    //----------------LogcatContent------------
    @Query("SELECT * FROM " + LogcatContent.TABLE_NAME + " WHERE " + LogcatContent.CONTENT_ID + " = :id")
    LiveData<LogcatContent> queryLogcatContent(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateLogcatContent(LogcatContent content);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLogcatContent(LogcatContent content);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertIfNotExits(LogcatContent content);
}
