package com.example.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GainsDao {

    @Insert
    void insertGains(Gains gains);

    @Update
    void updateGains(Gains gains);

    @Delete
    void deleteGains(Gains gains);

    @Query("SELECT * FROM gains WHERE user_id = :user_id and gains_type = :gains_type")
    List<Gains> getGainsType(int user_id, boolean gains_type);

    @Query("SELECT * FROM gains WHERE user_id = :user_id")
    List<Gains> getGains(int user_id);

    @Query("DELETE FROM gains")
    void deleteAllGains();
}
