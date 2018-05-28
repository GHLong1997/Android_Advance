package com.hangoclong.roompersistent.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Long on
 */

@Entity(foreignKeys = @ForeignKey(entity = Product.class,
                                            parentColumns = "id",
                                            childColumns = "product_id"))
public class ProductDetails {

    @PrimaryKey(autoGenerate = true)
    private int mId;

    @ColumnInfo(name = "product_id")
    private int product_id;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
