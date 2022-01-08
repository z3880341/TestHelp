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

    /**
     * 查询最新100条数据
     * @return
     */
    @Query("select * from NetworkBean order by id DESC limit 200")
    abstract List<NetworkBean> theLatestData();

    /**
     * 查询指定时间段
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("select * from NetworkBean where creationTime between :startTime and :endTime order by id DESC")
    abstract List<NetworkBean> searchByTime(long startTime, long endTime);

    /**
     * 模糊查询并且倒叙
     * @param content
     * @return
     */
    @Query("select * from NetworkBean where content like '%'||:content||'%' order by id DESC")
    abstract List<NetworkBean> searchByContain(String content);

    /**
     * 删除全部数据
     */
    @Query("delete from NetworkBean")
    public abstract void deleteAll();
}
