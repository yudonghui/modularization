package com.ydh.module_first.module;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.ydh.common.base.BaseModel;
import com.ydh.common.common.CommonSubscriber;
import com.ydh.common.rx.HttpRxObservable;
import com.ydh.module_first.bean.User;
import com.ydh.module_first.service.ApiService;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class FirstModule extends BaseModel {

    private final RxAppCompatActivity mActivity;
    @Inject
    ApiService apiService;

    @Inject
    public FirstModule(RxAppCompatActivity activity) {
        mActivity = activity;
    }

    public void web(HashMap<String, String> params, CommonSubscriber<User> subscriber) {
        Flowable<User> userFlowable = apiService.getUser(params);
        //被观察者
        Flowable observable = HttpRxObservable.getObservable(userFlowable, mActivity);
        observable.subscribe(subscriber);
    }
}
