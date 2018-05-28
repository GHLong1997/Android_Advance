package com.hangoclong.roompersistent.screen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.hangoclong.roompersistent.data.model.Product;
import com.hangoclong.roompersistent.data.repository.ProductRepository;
import com.hangoclong.roompersistent.utils.rx.BaseSchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Publisher;

/**
 * Created by Long on
 */

public class MainPresenter extends ViewModel implements MainContract.Presenter {

    private ProductRepository mProductRepository;
    private MainContract.View mView;
    private BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    MutableLiveData<List<Product>> mLiveData = new MutableLiveData<>();

    public MainPresenter(ProductRepository productRepository,
            BaseSchedulerProvider baseSchedulerProvider) {
        mProductRepository = productRepository;
        mSchedulerProvider = baseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(MainContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
        getAllProduct();
    }

    @Override
    public void onStop() {
        mCompositeDisposable.clear();
    }

    @Override
    public void getAllProduct() {
        Disposable disposable = mProductRepository.getAllProduct()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<Product>>() { //onNext
                    @Override
                    public void accept(List<Product> products) throws Exception {
                        Log.d("haha", "apply: " + Thread.currentThread().getName());
                        mView.showProducts(products);
                    }
                }, new Consumer<Throwable>() { //onError
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        mView.showError(throwable);
                    }
                }, new Action() {//onComplete
                    @Override
                    public void run() throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void insertProduct(final Product... products) {
        Disposable disposable =
                mProductRepository.insertProduct(products)  //sẽ trả về 1 Completable
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe(new Action() { //onComplete
                            @Override
                            public void run() throws Exception {
                                mView.updateSuccess();
                            }
                        }, new Consumer<Throwable>() { // onError
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                mView.showError(throwable);
                            }
                        });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void updateProduct(final Product... products) {
        Disposable disposable = mProductRepository.updateProduct(products)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.updateSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void deleteAllProduct() {
        Disposable disposable = mProductRepository.deleteAllProduct()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.deleteSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable);
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void searchProduct(final String name) {
        Disposable disposable = Flowable.defer(new Callable<Publisher<String>>() {
            @Override
            public Publisher<String> call() throws Exception {
                return Flowable.just(name);
            }
        })
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if (s.isEmpty()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                })
                .distinctUntilChanged()
                .switchMap(new Function<String, Publisher<List<Product>>>() {
                    @Override
                    public Publisher<List<Product>> apply(String s) throws Exception {
                        return mProductRepository.searchProduct(s);
                    }
                })
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> o) throws Exception {
                        Log.d("haha", "test: ");
                        mView.showProducts(o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void deleteProductById(final int id) {
        Disposable disposable = Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                mProductRepository.deleteProductById(id);
            }
        })
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.deleteSuccess();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError(throwable);
                    }
                })
                .subscribe();
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void getProductById(int id) {
        mProductRepository.getProductById(id)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Consumer<Product>() {//onSuccess
                    @Override
                    public void accept(Product product) throws Exception {

                    }
                }, new Consumer<Throwable>() {//onError
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }, new Action() {//onComplete
                    @Override
                    public void run() throws Exception {

                    }
                });
    }

    @Override
    public LiveData<List<Product>> getProducts() {
      return mProductRepository.getProductt();

    }

    private void processProducts(List<Product> products) {
        if (products == null) {
            mView.showNoProduct();
        } else {
            mView.showProducts(products);
        }
    }
}
