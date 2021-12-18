package com.wt.ytzn.testhelp.database;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.wt.ytzn.testhelp.app.App;

import java.util.List;

public class NetworkDBOperate {
    private static NetworkDBOperate sErrorDbOperate = null;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private NetworkDao mDao;

    public static NetworkDBOperate getInstance() {
        if (sErrorDbOperate == null) {
            synchronized (NetworkDBOperate.class) {
                if (sErrorDbOperate == null) {
                    sErrorDbOperate = new NetworkDBOperate();
                    sErrorDbOperate.initHandler();
                }
            }
        }
        return sErrorDbOperate;
    }

    private void initHandler() {
        mHandlerThread = new HandlerThread("DBOperate");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mDao = Database.getI(App.sApp).networkDao();
    }

    public void insert(NetworkBean... data) {
        mHandler.post(() -> mDao.insert(data));
    }

    public void theLatestData(Callback callback) {
        mHandler.post(() -> {
            if (callback != null) {
                List<NetworkBean> list = mDao.theLatestData();
                mMainHandler.post(() -> {
                    callback.onResult(list);
                });
            }
        });
    }

    public void searchByTime(long startTime, long endTime, Callback callback) {
        mHandler.post(() -> {
            if (callback != null) {
                List<NetworkBean> list = mDao.searchByTime(startTime, endTime);
                mMainHandler.post(() -> {
                    callback.onResult(list);
                });
            }
        });
    }

    public void searchByContain(String content, Callback callback) {
        mHandler.post(() -> {
            if (callback != null) {
                List<NetworkBean> list = mDao.searchByContain(content);
                mMainHandler.post(() -> {
                    callback.onResult(list);
                });
            }
        });
    }

    public void deleteAll() {
        mHandler.post(() -> mDao.deleteAll());
    }

    public static interface Callback {
        void onResult(List<NetworkBean> data);
    }

}
