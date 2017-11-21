package me.zane.fairy.binding;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.widget.TextView;

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
                    if (!s.equals("")) {
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
