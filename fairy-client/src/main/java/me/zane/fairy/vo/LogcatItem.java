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
package me.zane.fairy.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

@Entity(tableName = "logcat_item")
public class LogcatItem implements Parcelable{
    public static final String TABLE_NAME = "logcat_item";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "logcat_options")
    private String options;

    @ColumnInfo(name = "logcat_filter")
    private String filter;

    @ColumnInfo(name = "logcat_grep")
    private String grep;

    public LogcatItem(int id, String options, String filter, String grep) {
        this.id = id;
        this.options = options;
        this.filter = filter;
        this.grep = grep;
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

    public String getGrep() {
        return grep;
    }

    public void setGrep(String grep) {
        this.grep = grep;
    }

    public static LogcatItem creatEmpty(int id) {
        return new LogcatItem(id, "", "", "");
    }

    @Override
    public String toString() {
        return "id: " + id +
                       " options: " + options +
                       " filter: " + filter +
                       " grep: " + grep;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.options);
        dest.writeString(this.filter);
        dest.writeString(this.grep);
    }

    protected LogcatItem(Parcel in) {
        this.id = in.readInt();
        this.options = in.readString();
        this.filter = in.readString();
        this.grep = in.readString();
    }

    public static final Creator<LogcatItem> CREATOR = new Creator<LogcatItem>() {
        @Override
        public LogcatItem createFromParcel(Parcel source) {
            return new LogcatItem(source);
        }

        @Override
        public LogcatItem[] newArray(int size) {
            return new LogcatItem[size];
        }
    };
}
