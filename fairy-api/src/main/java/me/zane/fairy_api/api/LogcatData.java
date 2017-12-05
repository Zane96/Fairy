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
package me.zane.fairy_api.api;

/**
 * Created by Zane on 2017/10/24.
 * Email: zanebot96@gmail.com
 */

public class LogcatData {

    /**
     * data : --------- beginning of main
     10-24 16:02:50.621 11262 11271 I LiveNetService   : body2: options=-t 5&filter=&timeline=
     10-24 16:02:50.639 11262 11273 D LiveNetService   : start exec
     10-24 16:02:50.639  1492  1852 E Parcel  : Reading a NULL string not supported here.
     10-24 16:02:50.640 11262 11271 I LiveNetService   : Client Socket accept success from: /192.168.0.105
     10-24 16:02:50.720 11262 11273 I LiveNetService   : exec logcat command: logcat -d -t 5

     * timeLine : 10-24 16:02:50.720
     */

    private String data;
    private String timeLine;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    @Override
    public String toString() {
        return "timeline: " + timeLine + " data: " + data;
    }
}
