package me.zane.fairy.view.content;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.zane.fairy.R;
import me.zane.fairy.databinding.ItemContentBinding;
import me.zane.fairy.vo.LogcatContent;

/**
 * Created by Zane on 2018/5/18.
 * Email: zanebot96@gmail.com
 */
public class LogcatAdapter extends RecyclerView.Adapter<LogcatAdapter.LogcatViewHolder>{
    private List<LogcatContent> mDatas;
    private Context context;
    private LayoutInflater inflater;

    private OnLoadListener listener;

    public interface OnLoadListener {
        void finish(LogcatContent data);
    }

    public void setOnLoadListsner(OnLoadListener listsner) {
        this.listener = listsner;
    }

    public LogcatAdapter(Context context) {
        this.context = context;
        mDatas = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public LogcatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_content, parent, false);
        return new LogcatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LogcatViewHolder holder, int position) {
        holder.bindData(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(LogcatContent data) {
        mDatas.add(data);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public class LogcatViewHolder extends RecyclerView.ViewHolder {
        private ItemContentBinding binding;

        public LogcatViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bindData(LogcatContent data) {
            binding.setLogcatContent(data);
            binding.executePendingBindings();

            if (listener != null) {
                listener.finish(data);
            }
        }
    }
}
