package com.hangoclong.roompersistent.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.hangoclong.roompersistent.data.model.Product;
import com.hangoclong.roompersistent.data.source.ProductDataSource;
import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Action;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Long on
 */

public class ProductLocalDataSource implements ProductDataSource.LocalDataSource {

    private static ProductLocalDataSource mProductLocalDataSource;
    private ProductDAO mProductDAO;

    public ProductLocalDataSource (ProductDAO productDAO) {
        mProductDAO = productDAO;
    }

    public static synchronized ProductLocalDataSource getInstance(ProductDAO productDAO) {
        if (mProductLocalDataSource == null) {
            mProductLocalDataSource = new ProductLocalDataSource(productDAO);
        }
        return mProductLocalDataSource;
    }

    @Override
    public Flowable<List<Product>> getAllProduct() {
        return mProductDAO.getAllProduct();
    }

    @Override
    public Flowable<List<Product>> searchProduct(String name) {
        return mProductDAO.getProductByName(name);
    }

    @Override
    public Completable insertProduct(final Product...products) {
       return Completable.defer(new Callable<CompletableSource>() {
           @Override
           public CompletableSource call() throws Exception {
               return Completable.fromAction(new Action() {
                   @Override
                   public void run() throws Exception {
                       mProductDAO.insetProduct(products);
                   }
               });
           }
       });

    }

    @Override
    public Completable updateProduct(final Product...products) {
       return Completable.defer(new Callable<CompletableSource>() {
           @Override
           public CompletableSource call() throws Exception {
               return Completable.fromAction(new Action() {
                   @Override
                   public void run() throws Exception {
                       mProductDAO.updateProduct(products);
                   }
               });
           }
       });
    }

    @Override
    public Maybe<Product> getProductById(int id) {
        return mProductDAO.getProductById(id);
    }

    @Override
    public LiveData<List<Product>> getProductt() {
        return mProductDAO.getProducts1();
    }

    @Override
    public Completable deleteAllProduct() {

       return Completable.defer(new Callable<CompletableSource>() {
           @Override
           public CompletableSource call() throws Exception {
               return Completable.fromAction(new Action() {
                   @Override
                   public void run() throws Exception {
                       mProductDAO.deleteAllProduct();
                   }
               });
           }
       }) ;
    }

    @Override
    public void deleteProductById(int id) {
        mProductDAO.deleteProductById(id);
    }
}
