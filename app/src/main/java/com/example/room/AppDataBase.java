package com.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Gains.class}, version = 1, exportSchema = false)

public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract GainsDao gainsDao();

    public static AppDataBase INSTANCE;

    public static AppDataBase getDbInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "mpt.db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
