package com.ydh.module_first.bean;

import android.content.Context;

import com.ydh.common.bean.BaseModule;
import com.ydh.module_first.service.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule extends BaseModule {

    public ApiModule(Context application) {
        super(application);
    }

    @Provides
    public ApiService provideApiService() {
        return mRetrofit.create(ApiService.class);
    }
}
