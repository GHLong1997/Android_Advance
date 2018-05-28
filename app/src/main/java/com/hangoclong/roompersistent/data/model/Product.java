package com.hangoclong.roompersistent.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Long on
 */

@Entity(tableName = "product")
public class Product {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "number")
    private int mNumber;

    @ColumnInfo(name = "price")
    private int mPrice;

    public Product() {}

    public Product(String name, int number, int price) {
        mName = name;
        mNumber = number;
        mPrice = price;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

}
