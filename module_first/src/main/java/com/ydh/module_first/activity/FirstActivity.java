package com.ydh.module_first.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ydh.common.base.BaseActivity;
import com.ydh.common.bean.MessageEvent;
import com.ydh.common.rx.HttpRxObservable;
import com.ydh.common.utils.ApiException;
import com.ydh.common.utils.ExceptionEngine;
import com.ydh.module_first.R;
import com.ydh.module_first.bean.ActivityModule;
import com.ydh.module_first.bean.ApiModule;
import com.ydh.module_first.bean.User;
import com.ydh.module_first.component.DaggerActivityComponent;
import com.ydh.module_first.debug.FirstApp;
import com.ydh.module_first.presenter.FirstPresenter;
import com.ydh.module_first.service.ApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

public class FirstActivity extends BaseActivity<FirstPresenter> {
    private TextView mEventText;
    @Inject
    ApiService apiService;

    @Override
    protected void inject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .apiModule(new ApiModule(FirstApp.getContext()))
                .build()
                .inject(FirstActivity.this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_first;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mEventText = findViewById(R.id.eventText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void openSencond(View view) {
        try {
            Class<?> aClass = Class.forName("com.ydh.module_second.SecondActivity");
            startActivity(new Intent(FirstActivity.this, aClass));
        } catch (ClassNotFoundException e) {
            Toast.makeText(FirstActivity.this, "没有找到SecondActivity", Toast.LENGTH_SHORT).show();
        }
    }

    //eventbus 接收广播
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent event) {
        mEventText.setText(TextUtils.isEmpty(event.getMessage()) ? "空" : event.getMessage());
    }

    public void mvp(View view) {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("sort", "0");
        params.put("type", "glean");
        params.put("limit", "5");
        mPresenter.web(params);
    }

    public void setData(User testBean) {
        Log.e("结果", testBean.toString());
    }

    public void RetrofitRxjava(View view) {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("sort", "0");
        params.put("type", "glean");
        params.put("limit", "5");
        Flowable<User> userFlowable = apiService.getUser(params);
        //被观察者
        Flowable observable = HttpRxObservable.getObservable(userFlowable, this);
        observable.subscribe(new DisposableSubscriber<User>() {
            @Override
            public void onNext(User testBean) {
                Log.e("结果", testBean.toString());
            }

            @Override
            public void onError(Throwable t) {
                ApiException apiException = ExceptionEngine.handleException(t);
                Toast.makeText(FirstActivity.this, apiException.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        }/*new DisposableSubscriber<User>() {
            @Override
            public void onNext(User user) {
                Log.e("结果", user.toString());
            }

            @Override
            public void onError(Throwable t) {
                ApiException apiException = ExceptionEngine.handleException(t);
                Toast.makeText(FirstActivity.this, apiException.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        }*/);
    }

    @Override
    public void showToast(String msg) {

    }
}
