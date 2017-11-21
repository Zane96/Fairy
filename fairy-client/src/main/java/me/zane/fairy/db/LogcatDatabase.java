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
