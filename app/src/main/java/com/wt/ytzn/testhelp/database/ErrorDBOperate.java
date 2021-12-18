package com.wt.ytzn.testhelp.database;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.wt.ytzn.testhelp.app.App;

import java.util.List;

public class ErrorDBOperate {
    private static ErrorDBOperate sErrorDbOperate = null;
    private HandlerThread mHandlerThread;
    private Handler mHandler;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private ErrorDao mDao;

    public static ErrorDBOperate getInstance() {
        if (sErrorDbOperate == null){
            synchronized (ErrorDBOperate.class){
                if (sErrorDbOperate == null){
                    sErrorDbOperate = new ErrorDBOperate();
                    sErrorDbOperate.initHandler();
                }
            }
        }
        return sErrorDbOperate;
    }

    private void initHandler(){
        mHandlerThread = new HandlerThread("DBOperate");
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
        mDao = Database.getI(App.sApp).errorDao();
    }

    public void insert(ErrorBean... data){
        mHandler.post(()-> mDao.insert(data));
    }

    public void getAll(Callback callback){
        mHandler.post(()->{
            if (callback != null) {
                List<ErrorBean> list = mDao.getAll();
                mMainHandler.post(()->{
                    callback.onResult(list);
                });
            }
        });
    }

    public void deleteAll(){
        mHandler.post(()-> mDao.deleteAll());
    }

    public static interface Callback{
        void onResult(List<ErrorBean> data);
    }


}
