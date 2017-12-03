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
package me.zane.fairy.api;

import me.zane.fairy.Utils;
import rx.Observable;

/**
 * Created by Zane on 2017/10/30.
 * Email: zanebot96@gmail.com
 */

public class PollObCreater extends ObservaleCreater{

    public Observable<LogcatData> creat(String options, String filter, String grep) {
        String newOptions = options;
        if (!lastTimeLine.equals(DEFAULT_TIMELINE)) {
            if (!newOptions.contains("-t")) {
                String timeLine = getStartTimeLine();
                newOptions = options + String.format(" -t \"%s\"", timeLine);
            } else if (newOptions.contains("-t \"")) {
                String timeLine = getStartTimeLine();
                String oldTime = options.substring(options.indexOf("-t \""), options.indexOf("-t \"") + 23);
                newOptions = options.replace(oldTime, String.format("-t \"%s\"", timeLine));
            }
        }
        return LogcatModel.getInstance().logcat(newOptions, filter, grep);
    }

    private String getStartTimeLine() {
        String timeLine;
        timeLine = Utils.addOneMillsecond(lastTimeLine);
        startTimeLine = timeLine;

        return timeLine;
    }
}
