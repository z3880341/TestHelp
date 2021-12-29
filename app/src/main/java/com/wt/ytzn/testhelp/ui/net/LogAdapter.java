package com.wt.ytzn.testhelp.ui.net;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wt.ytzn.testhelp.database.NetworkBean;
import com.wt.ytzn.testhelp.databinding.ItemNetworkLogBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    private List<NetworkBean> mList = new ArrayList<>();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
    private int mCurrentClickPosition = -1;

    @SuppressLint("NotifyDataSetChanged")
    public void refreshData(List<NetworkBean> list) {
        mList.clear();
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNetworkLogBinding binding = ItemNetworkLogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (position == RecyclerView.NO_POSITION) {
                    return;
                }
                mCurrentClickPosition = position;
                notifyItemChanged(position);

            }
        });
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull LogAdapter.ViewHolder holder, int position) {
        NetworkBean errorBean = mList.get(position);
        Context context = holder.binding.content.getContext();
        holder.binding.content.setText(mDateFormat.format(errorBean.creationTime) + ":" + errorBean.content);
        if (mCurrentClickPosition == position) {
            holder.binding.content.setTextColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemNetworkLogBinding binding;

        public ViewHolder(@NonNull ItemNetworkLogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
