package com.wt.ytzn.testhelplib;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;

public class ErrorCollect implements Thread.UncaughtExceptionHandler {
    private static ErrorCollect errorCollect = new ErrorCollect();
    private Context context;

    public static ErrorCollect getInstance() {
        return errorCollect;
    }

    public void init(Context ctx) {
        context = ctx;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        e.printStackTrace();
        try {
            if (TestHelp.getInstance().isEnable()) {
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(e.toString() + "\n");
                for (StackTraceElement item : e.getStackTrace()) {
                    jsonArray.put(item.toString() + "\n");
                }
                Uri uri = Uri.parse(UriConfig.ERROR_URI);
                ContentValues contentValues = new ContentValues();
                contentValues.put("content", jsonArray.toString());
                context.getContentResolver().insert(uri, contentValues);
            }
        } catch (Exception e1) {
            e.printStackTrace();
            Log.e("ErrorCollect", "插入异常log失败, 异常原因:" + e1.getMessage());
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
