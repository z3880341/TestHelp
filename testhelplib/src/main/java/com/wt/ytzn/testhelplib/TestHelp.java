package com.wt.ytzn.testhelplib;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;

public class TestHelp {
    private static TestHelp sTestHelp;
    private boolean mEnable = false;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    public static TestHelp getInstance() {
        if (sTestHelp == null) {
            synchronized (TestHelp.class) {
                if (sTestHelp == null) {
                    sTestHelp = new TestHelp();
                }
            }
        }
        return sTestHelp;
    }

    public void setEnable(boolean mEnable) {
        this.mEnable = mEnable;
    }

    protected boolean isEnable() {
        return mEnable;
    }

    public void initErrorCollect(Context context) {
        if (!mEnable){
            return;
        }
        ErrorCollect.getInstance().init(context);
    }

    public void insertLog(Context context, String content) {
        if (!mEnable){
            return;
        }
        if (TextUtils.isEmpty(content.trim())){
            return;
        }
        try{
            Uri uri = Uri.parse(UriConfig.NETWORK_URI);
            ContentValues contentValues = new ContentValues();
            contentValues.put("content", content);
            context.getContentResolver().insert(uri, contentValues);
        } catch (Exception e){
            e.printStackTrace();
            mMainHandler.post(()-> Toast.makeText(context, "log插入失败,你可能没安装或者启动TestHelp", Toast.LENGTH_SHORT).show());
            Log.e("TestHelp", "插入log失败, 异常原因:" + e.getMessage());
        }
    }

}
