package com.ydh.module_first.presenter;

import android.widget.Toast;

import com.ydh.common.common.CommonSubscriber;
import com.ydh.common.utils.ApiException;
import com.ydh.module_first.activity.FirstActivity;
import com.ydh.module_first.bean.User;
import com.ydh.module_first.module.FirstModule;

import java.util.HashMap;

import javax.inject.Inject;

public class FirstPresenter extends RxPresenter<FirstActivity, FirstModule> {
    @Inject
    public FirstPresenter() {
    }
    public void web(HashMap<String, String> params) {
        mModel.web(params, new CommonSubscriber<User>() {


            @Override
            public void getData(User testBean) {
                mView.setData(testBean);
            }

            @Override
            public void error(ApiException e) {
                Toast.makeText(mView, e.getMsg(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
