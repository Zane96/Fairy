package me.zane.fairy.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            LogcatContent logcatContent = rawData.getValue();
            String rawContent = "";
            if (logcatContent != null && ((rawContent = logcatContent.getContent()).length() > 0)) {
                return new LogcatContent(0, parseHtml(rawContent, grep));
            }
            return new LogcatContent(0, rawContent);
        });
    }

    private static String parseHtml(String rawContent, String grep) {
        Pattern pattern = Pattern.compile("(<(p)><font(\\s[^\\t\\n\\r\\>]+)?>([^<>])*" + grep + "([^<>])*<\\/p>)+?");
        Matcher matcher = pattern.matcher(rawContent);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(matcher.group());
        }
        return stringBuilder.toString();
    }

    //    private static String parseHtml(String rawContent, String grep) {
//        Pattern pattern = Pattern.compile("<p>(.*?)</p>");
//        Matcher matcher = pattern.matcher(rawContent);
//        Pattern filterPattern = Pattern.compile(".*"+grep+".*");
//        StringBuilder stringBuilder = new StringBuilder();
//        while (matcher.find()) {
//            if (filterPattern.matcher(matcher.group()).matches()) {
//                stringBuilder.append(matcher.group());
//            }
//        }
//        return stringBuilder.toString();
//    }

    public String parseHtml1(String rawContent,String grep) {
        Pattern pattern = Pattern.compile("(<(p)><font(\\s[^\\t\\n\\r\\>]+)?>([^<>])*" + grep + "([^<>])*<\\/p>)+?");
        Matcher matcher = pattern.matcher(rawContent);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            stringBuilder.append(matcher.group());
        }
        return stringBuilder.toString();
    }

}
