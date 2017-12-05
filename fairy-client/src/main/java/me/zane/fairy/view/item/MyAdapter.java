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
package me.zane.fairy.view.item;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.zane.fairy.R;
import me.zane.fairy.databinding.ItemRecycleMainBinding;
import me.zane.fairy.vo.LogcatItem;

/**
 * Created by Zane on 2017/10/31.
 * Email: zanebot96@gmail.com
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<LogcatItem> datas;
    private LayoutInflater inflater;
    private Context context;
    private OnClickListener listener;
    private int lastId;

    public interface OnClickListener {
        void onClick(int position);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public MyAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(inflater.inflate(R.layout.item_recycle_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    LogcatItem get(int position) {
        return datas.get(position);
    }

    void addAll(List<LogcatItem> items) {
        datas.addAll(items);
        notifyDataSetChanged();
    }

    void replace(int position, LogcatItem item) {
        datas.remove(position);
        datas.add(position, item);
        notifyItemChanged(position);
    }

    void add(LogcatItem item) {
        datas.add(item);
        notifyItemChanged(datas.size() - 1);
    }

    void remove(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, datas.size());
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView mText;
        private ItemRecycleMainBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void setData(int position) {
            binding.setItem(datas.get(position));
        }
    }
}
