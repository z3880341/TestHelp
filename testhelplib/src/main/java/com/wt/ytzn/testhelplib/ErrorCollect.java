package com.wt.ytzn.testhelplib;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONArray;

public class ErrorCollect implements Thread.UncaughtExceptionHandler {
    private static ErrorCollect sErrorCollect;
    private Context mContext;
    private boolean mIsEnable = false;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    public static ErrorCollect getInstance() {
        if (sErrorCollect == null) {
            synchronized (ErrorCollect.class) {
                if (sErrorCollect == null) {
                    sErrorCollect = new ErrorCollect();
                }
            }
        }
        return sErrorCollect;
    }

    protected void init(Context ctx, boolean isEnable) {
        mContext = ctx;
        mIsEnable = isEnable;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        e.printStackTrace();
        try {
            if (mIsEnable) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.put("抛出异常线程Id = " + t.getId() + "\n");
                jsonArray.put("抛出异常线程Name = " + t.getName() + "\n");
                jsonArray.put(e.toString() + "\n");
                for (StackTraceElement item : e.getStackTrace()) {
                    jsonArray.put(item.toString() + "\n");
                }
                Uri uri = Uri.parse(UriConfig.ERROR_URI);
                ContentValues contentValues = new ContentValues();
                contentValues.put("content", jsonArray.toString());
                mContext.getContentResolver().insert(uri, contentValues);
            }
        } catch (Exception e1) {
            e.printStackTrace();
            mMainHandler.post(() -> Toast.makeText(mContext, "异常记录失败,你可能没安装或者启动TestHelp,原因:" + e1.getMessage(), Toast.LENGTH_SHORT).show());
            Log.e("ErrorCollect", "异常记录失败, 异常原因:" + e1.getMessage());
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
