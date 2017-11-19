package me.zane.fairy.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

@Entity(tableName = "logcat_item")
public class LogcatItem {
    public static final String TABLE_NAME = "logcat_item";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "logcat_options")
    private String options;

    @ColumnInfo(name = "logcat_filter")
    private String filter;

    public LogcatItem(int id, String options, String filter) {
        this.id = id;
        this.options = options;
        this.filter = filter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "id: " + id +
                       " options: " + options +
                       " filter: " + filter;
    }
}
