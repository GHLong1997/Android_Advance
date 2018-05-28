package com.hangoclong.roompersistent.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.hangoclong.roompersistent.data.model.Product;

import static com.hangoclong.roompersistent.data.source.local.ProductDatabase.DATABASE_VERSION;

/**
 * Created by Long on
 */

@Database(entities = Product.class, version = DATABASE_VERSION)
public abstract class ProductDatabase  extends RoomDatabase{
    private static ProductDatabase mProductDatabase;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ProductManager";

    public abstract ProductDAO productDAO();

    public static synchronized ProductDatabase getInstance(Context context) {
        if (mProductDatabase == null) {
            mProductDatabase = Room.databaseBuilder(context, ProductDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mProductDatabase;
    }

}
