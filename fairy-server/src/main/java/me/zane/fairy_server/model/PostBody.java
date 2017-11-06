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
package me.zane.fairy_server.model;

import java.util.HashMap;

import me.zane.fairy_server.ZLog;

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

    public static PostBody parse(String rawBody) {
        ZLog.d("raw body: " + rawBody);
        String body = formatBody(rawBody);

        ZLog.d("format body: " + body);
        HashMap<String, String> fieldMap = new HashMap<>();
        String[] fields = body.split("&");
        for (String field : fields) {
            String[] keyValue = field.split("=");
            if (keyValue.length == 1) {
                fieldMap.put(keyValue[0], "");
            } else if (keyValue.length == 3){
                //eg. --pid=22200
                fieldMap.put(keyValue[0], keyValue[1] + "=" + keyValue[2]);
            } else if (keyValue.length == 2){
                fieldMap.put(keyValue[0], keyValue[1]);
            }
        }

        return new PostBody(fieldMap);
    }

    public String getValue(String key) {
        return map.get(key);
    }

    private static String formatBody(String body) {
        if (body.contains("+")) {
            body = body.replace("+", " ");
        }
        if (body.contains("%3A")) {
            body = body.replace("%3A", ":");
        }
        //browser
        if (body.contains("%22")) {
            body = body.replace("%22", "\"");
        }
        if (body.contains("%3D")) {
            body = body.replace("%3D", "=");
        }
        //phone
        if (body.contains("%20")) {
            body = body.replace("%20", " ");
        }

        return body;
    }
}
