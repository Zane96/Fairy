package me.zane.fairy_server;

import java.util.HashMap;

/**
 * eg:
 * options=-t 5 -d
 * filter=ActivityManager:I *:S
 * timeline=10-16 13:46:55.161
 *
 * Created by Zane on 2017/10/16.
 * Email: zanebot96@gmail.com
 */

public class PostBody {
    public static final String OPTIONES_KEY = "options";
    public static final String FILTER_KEY = "filter";
    public static final String TIMELINE_KEY = "timeline";

    private HashMap<String, String> map;

    private PostBody(HashMap map){
        this.map = map;
    }

    public static PostBody parse(String body) {
        HashMap<String, String> fieldMap = new HashMap<>();
        String[] fields = body.split("&");
        for (String field : fields) {
            String[] keyValue = field.split("=");
            fieldMap.put(keyValue[0], keyValue[1]);
        }

        return new PostBody(fieldMap);
    }

    public String getValue(String key) {
        String value = map.get(key);
        return value != null ? value : "";
    }
}
