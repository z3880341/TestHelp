package com.wt.ytzn.testhelp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NetworkBean {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String content;

    public long creationTime;
}
