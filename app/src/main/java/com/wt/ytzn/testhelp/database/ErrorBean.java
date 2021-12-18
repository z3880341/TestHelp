package com.wt.ytzn.testhelp.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ErrorBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String content;

    public long creationTime;
}
