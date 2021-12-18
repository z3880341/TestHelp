package com.wt.ytzn.testhelp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class NetworkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(NetworkBean... data);

    @Update
    public abstract void update(NetworkBean... data);

    @Delete
    public abstract void delete(NetworkBean... data);

    @Query("select * from NetworkBean order by id DESC limit 100")
    abstract List<NetworkBean> theLatestData();

    @Query("select * from NetworkBean where creationTime between :startTime and :endTime")
    abstract List<NetworkBean> searchByTime(long startTime, long endTime);

    @Query("select * from NetworkBean where content like '%'||:content||'%'")
    abstract List<NetworkBean> searchByContain(String content);

    /**
     * 删除全部数据
     */
    @Query("delete from NetworkBean")
    public abstract void deleteAll();
}
