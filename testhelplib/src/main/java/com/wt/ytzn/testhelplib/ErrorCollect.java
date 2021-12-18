package com.wt.ytzn.testhelplib;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

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
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(e.toString() + "\n");
        for (StackTraceElement item : e.getStackTrace()) {
            jsonArray.put(item.toString() + "\n");
        }
        Uri uri = Uri.parse(UriConfig.ERROR_URI);
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", jsonArray.toString());
        context.getContentResolver().insert(uri, contentValues);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
