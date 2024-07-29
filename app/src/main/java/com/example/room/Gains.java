package com.example.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "gains", foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id_user", childColumns = "user_id", onDelete = ForeignKey.CASCADE))
public class Gains {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_gains")
    private int id;
    @ColumnInfo(name = "gains_type")
    private boolean gainsType;
    @ColumnInfo(name = "gains_info")
    private String gainsInfo;
    @ColumnInfo(name = "gains_price")
    private float gainsPrice;
    //@ForeignKey()
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Gains(boolean gainsType, String gainsInfo, float gainsPrice, int user_id) {
        this.gainsType = gainsType;
        this.gainsInfo = gainsInfo;
        this.gainsPrice = gainsPrice;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGainsInfo() {
        return gainsInfo;
    }

    public void setGainsInfo(String gainsInfo) {
        this.gainsInfo = gainsInfo;
    }

    public float getGainsPrice() {
        return gainsPrice;
    }

    public void setGainsPrice(float gainsPrice) {
        this.gainsPrice = gainsPrice;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isGainsType() {
        return gainsType;
    }

    public void setGainsType(boolean gainsType) {
        this.gainsType = gainsType;
    }
}
