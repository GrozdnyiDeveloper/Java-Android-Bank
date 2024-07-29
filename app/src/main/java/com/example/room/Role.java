package com.example.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "role")
public class Role {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_role")
    private int id;
    @ColumnInfo(name = "role_name")
    private String userName;

    public Role(String userName) {
        this.userName = userName;
    }

    @Ignore
    public Role(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
