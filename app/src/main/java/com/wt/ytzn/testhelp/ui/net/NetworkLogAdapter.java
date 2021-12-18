package com.wt.ytzn.testhelp.ui.net;


import android.annotation.SuppressLint;
import android.text.TextUtils;
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

public class NetworkLogAdapter extends RecyclerView.Adapter<NetworkLogAdapter.ViewHolder> {
    private List<NetworkBean> mList = new ArrayList<>();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

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
    public NetworkLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNetworkLogBinding binding = ItemNetworkLogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull NetworkLogAdapter.ViewHolder holder, int position) {
        NetworkBean errorBean = mList.get(position);
        holder.binding.content.setText(mDateFormat.format(errorBean.creationTime) + ":" + errorBean.content);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemNetworkLogBinding binding;

        public ViewHolder(@NonNull ItemNetworkLogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
