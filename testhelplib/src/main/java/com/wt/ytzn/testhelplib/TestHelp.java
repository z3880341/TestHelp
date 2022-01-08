package com.wt.ytzn.testhelplib;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class TestHelp {
    private Context mContext;
    private boolean mEnableLogCollect = false;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    private TestHelp(Build build){
        mContext = build.context;
        initErrorCollect(build.mEnableErrorCollect);
        mEnableLogCollect = build.mEnableLogCollect;
    }

    private void initErrorCollect(boolean isEnable) {
        if (isEnable) {
            ErrorCollect.getInstance().init(mContext, isEnable);
        }
    }

    public void insertLog(String content) {
        if (!mEnableLogCollect){
            return;
        }
        if (TextUtils.isEmpty(content.trim())){
            return;
        }
        try{
            Uri uri = Uri.parse(UriConfig.NETWORK_URI);
            ContentValues contentValues = new ContentValues();
            contentValues.put("content", content);
            mContext.getContentResolver().insert(uri, contentValues);
        } catch (Exception e){
            e.printStackTrace();
            mMainHandler.post(()-> Toast.makeText(mContext, "log插入失败,你可能没安装或者启动TestHelp", Toast.LENGTH_SHORT).show());
            Log.e("TestHelp", "插入log失败, 异常原因:" + e.getMessage());
        }
    }

    public static class Build{
        private Context context;
        private boolean mEnableErrorCollect = false;
        private boolean mEnableLogCollect = false;

        public Build(Context context){
            this.context = context;
        }

        public Build enableErrorCollect(boolean isEnable){
            mEnableErrorCollect = isEnable;
            return this;
        }

        public Build enableLogCollect(boolean isEnable){
            mEnableLogCollect = isEnable;
            return this;
        }

        public TestHelp build(){
            return new TestHelp(this);
        }
    }
}
