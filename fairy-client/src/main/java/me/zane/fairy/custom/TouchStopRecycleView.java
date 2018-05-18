package me.zane.fairy.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Zane on 2018/5/18.
 * Email: zanebot96@gmail.com
 */
public class TouchStopRecycleView extends RecyclerView{
    private boolean isCanSmoothScrolling = true;

    public TouchStopRecycleView(Context context) {
        super(context);
    }

    public TouchStopRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchStopRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isCanSmoothScrolling() {
        return isCanSmoothScrolling;
    }

    public void setCanSmoothScrolling(boolean canSmoothScrolling) {
        isCanSmoothScrolling = canSmoothScrolling;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setCanSmoothScrolling(false);
                break;
            case MotionEvent.ACTION_UP:
                setCanSmoothScrolling(true);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
