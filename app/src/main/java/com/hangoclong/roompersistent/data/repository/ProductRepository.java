package com.hangoclong.roompersistent.data.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.hangoclong.roompersistent.data.model.Product;
import com.hangoclong.roompersistent.data.source.ProductDataSource;
import com.hangoclong.roompersistent.data.source.local.ProductLocalDataSource;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.List;

/**
 * Created by Long on
 */

public class ProductRepository implements ProductDataSource.LocalDataSource {

    private static ProductRepository mRepository;

    @NonNull
    private ProductLocalDataSource mLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    @Nullable
    private List<Product> mCachedProduct;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    @VisibleForTesting
    boolean mCacheIsDirty = false;

    public ProductRepository(ProductLocalDataSource productLocalDataSource) {
        mLocalDataSource = productLocalDataSource;
    }

    public static ProductRepository getInstance(ProductLocalDataSource productLocalDataSource) {
       if (mRepository == null) {
           mRepository = new ProductRepository(productLocalDataSource);
       }
       return mRepository;
    }

    @Override
    public Flowable<List<Product>> getAllProduct() {
        return mLocalDataSource.getAllProduct();
    }

    @Override
    public Flowable<List<Product>> searchProduct(String name) {
        return mLocalDataSource.searchProduct(name);
    }

    @Override
    public Completable insertProduct(Product...products) {
        return mLocalDataSource.insertProduct(products);

    }

    @Override
    public Completable updateProduct(Product... products) {
       return mLocalDataSource.updateProduct(products);
    }

    @Override
    public Maybe<Product> getProductById(int id) {
        return mLocalDataSource.getProductById(id);
    }

    @Override
    public LiveData<List<Product>> getProductt() {
        return mLocalDataSource.getProductt();
    }

    @Override
    public Completable deleteAllProduct() {
       return mLocalDataSource.deleteAllProduct();
    }

    @Override
    public void deleteProductById(int id) {
        mLocalDataSource.deleteProductById(id);
    }
}
