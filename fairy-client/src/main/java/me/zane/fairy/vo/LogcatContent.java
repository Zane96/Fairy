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
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import me.zane.fairy.vo.LogcatItem;

/**
 * Created by Zane on 2017/11/16.
 * Email: zanebot96@gmail.com
 */

@Entity(tableName = "logcat_content", foreignKeys = {@ForeignKey(entity = LogcatItem.class,
                                                                 parentColumns = "id",
                                                                 childColumns = "content_id",
                                                                 onDelete = ForeignKey.CASCADE)})
public class LogcatContent {
    public static final String TABLE_NAME = "logcat_content";
    public static final String CONTENT_ID = "content_id";

    @PrimaryKey
    @ColumnInfo(name = CONTENT_ID)
    private int contentId;

    @ColumnInfo(name = "content")
    private String content;

    private boolean isFirst;

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

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    @Override
    public String toString() {
        return "contentId: " + contentId +
                       " content: " + content +
                       " timeLine: " + "";
    }
}
