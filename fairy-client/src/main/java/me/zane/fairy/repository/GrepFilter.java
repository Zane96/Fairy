package me.zane.fairy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import me.zane.fairy.vo.LogcatContent;

/**
 * Grep进行过滤
 * Created by Zane on 2017/11/30.
 * Email: zanebot96@gmail.com
 */

public class GrepFilter {

    static LiveData<LogcatContent> grepData(LiveData<LogcatContent> rawData, String grep) {

        return Transformations.map(rawData, logcatData -> {

            // TODO: 2017/11/30 grep logic
            return new LogcatContent(0, "");
        });
    }
}
