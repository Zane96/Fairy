package me.zane.fairy.resource;

import android.arch.lifecycle.LiveData;

import me.zane.fairy.db.LogcatContent;
import me.zane.fairy.db.LogcatDao;
import me.zane.fairy.db.LogcatDatabase;

/**
 * 貌似这一层的resource没必要抽象出来,em...，直接让merge依赖Dao
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

public class ContentLocalResource {
    private final LogcatDao logcatDao;

    private ContentLocalResource() {
        logcatDao = LogcatDatabase.getInstance().logcatDao();
    }

    private static final class SingltonHolder {
        private static final ContentLocalResource instance = new ContentLocalResource();
    }

    public static ContentLocalResource getInstance() {
        return SingltonHolder.instance;
    }

    public LiveData<LogcatContent> queryLogcatContent(int id) {
        return logcatDao.queryLogcatContent(id);
    }

    public void updateLogcatContent(LogcatContent content) {
        logcatDao.updateLogcatContent(content);
    }
}
