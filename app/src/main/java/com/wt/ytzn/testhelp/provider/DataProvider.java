package com.wt.ytzn.testhelp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wt.ytzn.testhelp.database.ErrorDBOperate;
import com.wt.ytzn.testhelp.database.ErrorBean;
import com.wt.ytzn.testhelp.database.NetworkBean;
import com.wt.ytzn.testhelp.database.NetworkDBOperate;

public class DataProvider extends ContentProvider {
    private static final String BASE_URL = "com.wt.ytzn.testhelp.provider";
    private static final String ERROR_PATH = "error";
    private static final String NETWORK_PATH = "network";
    private static final int ERROR_CODE = 0;
    private static final int NETWORK_CODE = 1;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(BASE_URL, ERROR_PATH, ERROR_CODE);
        uriMatcher.addURI(BASE_URL, NETWORK_PATH, NETWORK_CODE);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri)){
            case ERROR_CODE:
                ErrorBean errorBean = new ErrorBean();
                errorBean.content = values.getAsString("content");
                errorBean.creationTime = System.currentTimeMillis();
                ErrorDBOperate.getInstance().insert(errorBean);
                break;
            case NETWORK_CODE:
                NetworkBean networkBean = new NetworkBean();
                networkBean.content = values.getAsString("content");
                networkBean.creationTime = System.currentTimeMillis();
                NetworkDBOperate.getInstance().insert(networkBean);
                break;
            default:
                break;
        }

        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
