package com.hangoclong.roompersistent.data.source;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.hangoclong.roompersistent.data.model.Product;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.List;

/**
 * Created by Long on
 */

public interface ProductDataSource {

    interface LocalDataSource extends ProductDataSource {
        Flowable<List<Product>> getAllProduct();

        Flowable<List<Product>> searchProduct(String name);

        Completable insertProduct(Product...products);

        Completable deleteAllProduct();

        Completable updateProduct(Product...products);

        Maybe<Product> getProductById(int id);

        LiveData<List<Product>> getProductt();

        void deleteProductById(int id);
    }

    interface RemoteDataSource extends ProductDataSource {

    }

}
