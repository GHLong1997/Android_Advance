package com.hangoclong.httpurlconnection.screen;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
