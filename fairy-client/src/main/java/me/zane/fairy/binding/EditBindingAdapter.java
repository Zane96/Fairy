package me.zane.fairy.binding;

import android.databinding.BindingAdapter;
import android.view.View;
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
                ZLog.d("no match");
        }
    }
}
