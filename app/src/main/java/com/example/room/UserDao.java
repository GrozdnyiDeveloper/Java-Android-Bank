package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE user_login = :user_login")
    User getUser(String user_login);

    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("DELETE FROM user")
    void deleteAll();
}
