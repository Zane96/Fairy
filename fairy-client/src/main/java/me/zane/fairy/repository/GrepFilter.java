package me.zane.fairy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.MainThread;

import me.zane.fairy.vo.LogcatContent;
import retrofit2.http.PUT;

/**
 * Grep进行过滤
 * Created by Zane on 2017/11/30.
 * Email: zanebot96@gmail.com
 */

class GrepFilter {
    static final String RAW_SIGNAL = "raw content--------------\n";
    static final String GREP_SIGNAL = "grep content--------------\n";

    @MainThread
    static LiveData<LogcatContent> grepData(LiveData<LogcatContent> rawData, String grep) {
        return Transformations.map(rawData, logcatData -> {
            String content = logcatData.getContent();
            if (GREP_SIGNAL.equals(content)) {
                return logcatData;
            }

            // TODO: 2017/11/30 grep logic
            String grepContent = content;
            logcatData.setContent(grepContent);
            return logcatData;
        });
    }
}
