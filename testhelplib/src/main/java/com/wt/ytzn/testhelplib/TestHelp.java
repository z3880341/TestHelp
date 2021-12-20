package com.wt.ytzn.testhelplib;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;

public class TestHelp {
    private static TestHelp sTestHelp;

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

    public void initErrorCollect(Context context) {
        ErrorCollect.getInstance().init(context);
    }

    public void insertLog(Context context, String content) {
        if (TextUtils.isEmpty(content.trim())){
            return;
        }
        Uri uri = Uri.parse(UriConfig.NETWORK_URI);
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        context.getContentResolver().insert(uri, contentValues);

    }


}
