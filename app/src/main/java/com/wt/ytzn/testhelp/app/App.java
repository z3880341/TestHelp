package com.wt.ytzn.testhelp.app;

import android.app.Application;

public class App extends Application {
    public static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }
}
