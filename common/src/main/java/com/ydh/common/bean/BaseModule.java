package com.ydh.common.bean;

import android.content.Context;

import com.ydh.common.interceptor.RequestParamInterceptor;
import com.ydh.common.interceptor.RequestUrlInterceptor;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class BaseModule {
    protected Retrofit mRetrofit;

    public static String BASE_URL="http://api.dev.ixungen.cn/";
    public BaseModule(Context application){
        //原生Log日志拦截
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        if (BuildConfig.DEBUG) {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
        //请求参数拦截

        OkHttpClient okHttpClient = null;
        try {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(new RequestParamInterceptor())//对请求的参数进一步定制
                    .addInterceptor(new RequestUrlInterceptor())//对请求的接口进一步定制
                    .addInterceptor(loggingInterceptor)
                    //其他配置
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Retrofit初始化
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
