package com.wt.ytzn.testhelp.ui.error;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.wt.ytzn.testhelp.database.ErrorBean;
import com.wt.ytzn.testhelp.database.ErrorDBOperate;
import com.wt.ytzn.testhelp.databinding.ActivityErrorLogBinding;

import java.util.List;

public class ErrorLogActivity extends AppCompatActivity {
    private ActivityErrorLogBinding mBinding;
    private ErrorLogAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityErrorLogBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.back.setOnClickListener(v -> finish());
        mBinding.errorList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ErrorLogAdapter();
        mBinding.errorList.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ErrorDBOperate.getInstance().getAll(new ErrorDBOperate.Callback() {
            @Override
            public void onResult(List<ErrorBean> data) {
                mAdapter.refreshData(data);
            }
        });
    }
}