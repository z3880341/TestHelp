package com.wt.ytzn.testhelp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {ErrorBean.class, NetworkBean.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract ErrorDao errorDao();

    public abstract NetworkDao networkDao();

    private static Database mAppDataBase;

    public static Database getI(Context context) { //实现单例模式
        if (mAppDataBase == null) {
            mAppDataBase = Room.databaseBuilder(context, Database.class, "data.db")//data.db 是你的数据库名称
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mAppDataBase;
    }
}
