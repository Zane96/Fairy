package me.zane.fairy.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

@Entity(tableName = "logcat_content", foreignKeys = {@ForeignKey(entity = LogcatItem.class,
                                                                 parentColumns = "id",
                                                                 childColumns = "content_id",
                                                                 onDelete = ForeignKey.CASCADE)})
public class LogcatContent {
    static final String TABLE_NAME = "logcat_content";
    static final String CONTENT_ID = "content_id";

    @PrimaryKey
    @ColumnInfo(name = CONTENT_ID)
    private int contentId;

    @ColumnInfo(name = "content")
    private String content;

    public LogcatContent(int contentId, String content) {
        this.contentId = contentId;
        this.content = content;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "contentId: " + contentId +
                       " content: " + content;
    }
}
