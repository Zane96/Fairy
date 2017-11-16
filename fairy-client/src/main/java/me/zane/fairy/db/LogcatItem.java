package me.zane.fairy.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

@Entity(tableName = "logcat_item")
public class LogcatItem {
    static final String TABLE_NAME = "logcat_item";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "logcat_command")
    private String command;

    public LogcatItem(int id, String command) {
        this.id = id;
        this.command = command;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "id: " + id +
                       " command: " + command;
    }
}
