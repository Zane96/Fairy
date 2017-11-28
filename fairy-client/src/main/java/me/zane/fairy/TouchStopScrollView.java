package me.zane.fairy;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Zane on 2017/11/28.
 * Email: zanebot96@gmail.com
 */

public class TouchStopScrollView extends ScrollView{
    public TouchStopScrollView(Context context) {
        super(context);
    }

    public TouchStopScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchStopScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSmoothScrollingEnabled(false);
                break;
            case MotionEvent.ACTION_UP:
                setSmoothScrollingEnabled(true);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
