package com.example.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_user")
    private int id;
    @ColumnInfo(name = "user_login")
    private String login;
    @ColumnInfo(name = "user_password")
    private String password;
    @ColumnInfo(name = "user_name")
    private String userName;
    @ColumnInfo(name = "user_balance")
    private float userBalance;

    @Ignore
    public User(String login, String password, String userName, float userBalance) {
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.userBalance = userBalance;
    }

    public User(int id, String login, String password, String userName, float userBalance) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.userName = userName;
        this.userBalance = userBalance;
    }

    public float getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(float userBalance) {
        this.userBalance = userBalance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
