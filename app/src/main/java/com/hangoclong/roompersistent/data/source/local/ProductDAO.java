package com.hangoclong.roompersistent.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.hangoclong.roompersistent.data.model.Product;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

/**
 * Created by Long on
 */
@Dao
public interface ProductDAO  {

   @Query("SELECT * FROM product")
   Flowable<List<Product>> getAllProduct();

   @Query("DELETE FROM product")
   void deleteAllProduct();

   @Query("SELECT * FROM product WHERE name LIKE :name")
   Flowable<List<Product>> getProductByName(String name);

   @Query("SELECT * FROM product")
   Single<List<Product>> getProducts();

   @Query("DELETE FROM product WHERE id = :id")
   void deleteProductById(int id);

   @Insert
   void insetProduct(Product... products);

   @Update
   void updateProduct(Product... products);

   @Query("SELECT * FROM   product where id = :productId")
   Maybe<Product> getProductById(int productId);

   @Query("SELECT * FROM product")
   LiveData<List<Product>> getProducts1();

}
