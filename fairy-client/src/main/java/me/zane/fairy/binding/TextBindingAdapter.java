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
package me.zane.fairy.binding;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.widget.TextView;

import me.zane.fairy.Config;
import me.zane.fairy.R;

/**
 * Created by Zane on 2017/11/20.
 * Email: zanebot96@gmail.com
 */

public class TextBindingAdapter {
    @BindingAdapter("android:text")
    public static void append(TextView view, CharSequence s) {
        switch (view.getId()) {
            case R.id.text_data_logcat:
                if (s != null) {
                    if (s.equals(Config.CLEAR_SIGNAL)) {
                        view.setText("clear data");
                    } else if (!s.equals("")) {
                        view.append(Html.fromHtml(s.toString()));
                    }
                }
                break;
            default:
                view.setText(s);
                break;
        }
    }

}
