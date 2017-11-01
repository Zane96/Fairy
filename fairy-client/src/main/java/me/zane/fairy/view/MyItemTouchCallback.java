package me.zane.fairy.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import me.zane.fairy.Utils;

/**
 * Created by Zane on 2017/10/31.
 * Email: zanebot96@gmail.com
 */

public class MyItemTouchCallback extends ItemTouchHelper.SimpleCallback {
    //private Deque<ItemBean> queue;
    private MyAdapter adapter;
    private Context context;
    private boolean isSwap = false;

    public MyItemTouchCallback(Context context, MyAdapter adapter, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        //queue = new ArrayDeque<>(3);
        this.adapter = adapter;
        this.context = context;
    }

    //拖拽移动
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        if (fromPosition < toPosition) {
            //分别把中间所有的 item 的位置重新交换
            for (int i = fromPosition; i < toPosition; i++) {
                adapter.swap(i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                adapter.swap(i, i - 1);
            }
        }

        adapter.notifyItemMoved(fromPosition, toPosition);
        Utils.swapSp(fromPosition, toPosition);
        return true;
    }


    //处理动画
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变 Item 的透明度，以实现滑动过程中实现渐变效果
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    //斜滑删除
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        //入队列
        //queue.offerLast(bean);
        adapter.remove(position);
        adapter.notifyItemRemoved(position);
        Utils.moveOneSp(position);
        isSwap = true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (isSwap) {
            Toast.makeText(context, "已删除", Toast.LENGTH_SHORT).show();
            isSwap = false;
        }
    }
}
