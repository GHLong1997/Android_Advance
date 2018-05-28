package com.hangoclong.roompersistent.screen;

import android.arch.lifecycle.LiveData;
import com.hangoclong.roompersistent.data.model.Product;
import java.util.List;

/**
 * Created by Long on
 */

public interface MainContract {
    interface View {
        void showProducts(List<Product> products);

        void showNoProduct();

        void showError(Throwable throwable);

        void deleteSuccess();

        void updateSuccess();

    }

    interface Presenter {

        void setView(View view);

        void onStart();

        void onStop();

        void insertProduct(Product...products);

        void updateProduct(Product... products);

        void deleteAllProduct();

        void searchProduct(String name);

        void deleteProductById(int id);

        void getAllProduct();

        void getProductById(int id);

        LiveData<List<Product>> getProducts();
    }
}
