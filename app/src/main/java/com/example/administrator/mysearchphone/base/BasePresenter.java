package com.example.administrator.mysearchphone.base;

/**
 * Created by Administrator on 2017-08-24 0024.
 */
public class BasePresenter<V> {
    public V mView;

    public void attach(V view) {
        this.mView = view;
    }

    public void dettach() {
        this.mView = null;

    }
}
