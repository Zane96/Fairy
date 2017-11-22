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
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import me.zane.fairy.R;
import me.zane.fairy.ZLog;

/**
 * Created by Zane on 2017/11/20.
 * Email: zanebot96@gmail.com
 */

public class EditBindingAdapter {
    @BindingAdapter("android:hint")
    public static void setHint(EditText view, CharSequence hint) {
        switch (view.getId()) {
            case R.id.edit_options_logcat:
                String options = "[options]";
                if (!hint.equals("")) {
                    options = hint.toString();
                }
                view.setHint(options);
                break;
            case R.id.edit_filter_logcat:
                String filter = "[filterspecs]";
                if (!hint.equals("")) {
                    filter = hint.toString();
                }
                view.setHint(filter);
                break;
            default:
                ZLog.e("no match");
                break;
        }
    }
}
