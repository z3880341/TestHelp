package com.wt.ytzn.testhelp.ui.net;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wt.ytzn.testhelp.database.NetworkBean;
import com.wt.ytzn.testhelp.database.NetworkDBOperate;
import com.wt.ytzn.testhelp.databinding.ActivityLogBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LogActivity extends AppCompatActivity {
    private ActivityLogBinding mBinding;
    private LogAdapter mAdapter;
    private Calendar mStartCalender = Calendar.getInstance();
    private Calendar mEndCalender = Calendar.getInstance();
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat mTimeFormat = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLogBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.back.setOnClickListener(v -> finish());
        mBinding.errorList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LogAdapter();
        mBinding.errorList.setAdapter(mAdapter);
        mBinding.startDate.setText(mDateFormat.format(mStartCalender.getTimeInMillis()));
        mBinding.endDate.setText(mDateFormat.format(mEndCalender.getTimeInMillis()));
        mBinding.startDate.setOnClickListener(v -> {
            showDatePickerDialog(true);
        });
        mBinding.startTime.setOnClickListener(v -> {
            showTimeDialog(true);
        });
        mBinding.endDate.setOnClickListener(v -> {
            showDatePickerDialog(false);
        });
        mBinding.endTime.setOnClickListener(v -> {
            showTimeDialog(false);
        });
        mBinding.timeInquiry.setOnClickListener(v -> {
            if (TextUtils.equals(mBinding.startTime.getText(),"开始时间")){
                Toast.makeText(LogActivity.this, "请选择开始时间", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.equals(mBinding.endTime.getText(),"结束时间")){
                Toast.makeText(LogActivity.this, "请选择结束时间", Toast.LENGTH_SHORT).show();
                return;
            }
            NetworkDBOperate.getInstance().searchByTime(mStartCalender.getTimeInMillis(), mEndCalender.getTimeInMillis(), new NetworkDBOperate.Callback() {
                @Override
                public void onResult(List<NetworkBean> data) {
                    mAdapter.refreshData(data);
                }
            });
        });
        mBinding.keywordInquiry.setOnClickListener(v -> {
            String content = mBinding.keyword.getText().toString();
            if (TextUtils.isEmpty(content.trim())){
                Toast.makeText(LogActivity.this, "你没有输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            NetworkDBOperate.getInstance().searchByContain(content, new NetworkDBOperate.Callback() {
                @Override
                public void onResult(List<NetworkBean> data) {
                    mAdapter.refreshData(data);
                }
            });
        });
        mBinding.getTheLatest.setOnClickListener(v -> {
            NetworkDBOperate.getInstance().theLatestData(new NetworkDBOperate.Callback() {
                @Override
                public void onResult(List<NetworkBean> data) {
                    mAdapter.refreshData(data);
                }
            });

        });
    }

    /**
     * 日期选择
     */
    public void showDatePickerDialog(boolean isStart) {
        new DatePickerDialog(this, 0, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (isStart) {
                    mBinding.startDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mStartCalender.set(Calendar.YEAR, year);
                    mStartCalender.set(Calendar.MONTH, monthOfYear);
                    mStartCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                } else {
                    mBinding.endDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    mEndCalender.set(Calendar.YEAR, year);
                    mEndCalender.set(Calendar.MONTH, monthOfYear);
                    mEndCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                }
            }
        }, mStartCalender.get(Calendar.YEAR), mStartCalender.get(Calendar.MONTH), mStartCalender.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void showTimeDialog(boolean isStart){
        int hour = 0;
        int minute = 0;
        if (isStart){
            hour = mStartCalender.get(Calendar.HOUR_OF_DAY);
            minute = mStartCalender.get(Calendar.MINUTE);
        } else {
            hour = mEndCalender.get(Calendar.HOUR_OF_DAY);
            minute = mEndCalender.get(Calendar.MINUTE);

        }
        new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (isStart){
                    mStartCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    mStartCalender.set(Calendar.MINUTE, minute);
                    mBinding.startTime.setText(mTimeFormat.format(mStartCalender.getTimeInMillis()));
                } else {
                    mEndCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    mEndCalender.set(Calendar.MINUTE, minute);
                    mBinding.endTime.setText(mTimeFormat.format(mEndCalender.getTimeInMillis()));
                }
            }
        }, hour, minute, true).show();
    }
}