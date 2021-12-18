package com.wt.ytzn.testhelp.ui.error;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wt.ytzn.testhelp.database.ErrorBean;
import com.wt.ytzn.testhelp.databinding.ItemErrorLogBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ErrorLogAdapter extends RecyclerView.Adapter<ErrorLogAdapter.ViewHolder> {
    private List<ErrorBean> mList = new ArrayList<>();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());

    @SuppressLint("NotifyDataSetChanged")
    public void refreshData(List<ErrorBean> list) {
        mList.clear();
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ErrorLogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemErrorLogBinding binding = ItemErrorLogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ErrorLogAdapter.ViewHolder holder, int position) {
        ErrorBean errorBean = mList.get(position);
        holder.binding.time.setText(mDateFormat.format(errorBean.creationTime));
        try {
            StringBuilder sb = new StringBuilder();
            JSONArray jsonArray = new JSONArray(errorBean.content);
            for (int i = 0; i < jsonArray.length(); i++) {
                String item = jsonArray.getString(i);
                sb.append(item);
            }
            holder.binding.content.setText(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemErrorLogBinding binding;

        public ViewHolder(@NonNull ItemErrorLogBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
