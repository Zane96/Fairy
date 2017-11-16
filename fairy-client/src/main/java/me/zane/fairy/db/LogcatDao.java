package me.zane.fairy.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

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
}
