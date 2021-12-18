package com.wt.ytzn.testhelp.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Dao;

import java.util.List;

@Dao
public abstract class ErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ErrorBean... data);

    @Update
    public abstract void update(ErrorBean... data);

    @Delete
    public abstract void delete(ErrorBean... data);

    @Query("select * from ErrorBean")
    abstract List<ErrorBean> getAll();

    /**
     * 删除全部数据
     */
    @Query("delete from ErrorBean")
    public abstract void deleteAll();
}
