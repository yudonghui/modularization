package com.ydh.module_first.presenter;

import com.ydh.common.base.BaseModel;
import com.ydh.common.base.BasePresenter;
import com.ydh.common.base.BaseView;

import javax.inject.Inject;

public class RxPresenter<T extends BaseView, V extends BaseModel> implements BasePresenter<T, V> {
    @Inject
    protected V mModel;

    protected T mView;

    public RxPresenter() {
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }
}
