package com.ydh.common.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tie on 2017/5/17.
 */
public class RequestParamInterceptor implements Interceptor {
    private static final String TAG = "RequestParamInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        //请求定制：添加请求头
        Request.Builder requestBuilder = original.newBuilder();
       /* Map<String, Object> map = new HashMap<>();
        map.put("token", "");
        map.put("phone", "");
        map.put("requestId", UUID.randomUUID().toString());
        map.put("osversion", "android");
        map.put("appName", 4);
        String json = new Gson().toJson(map);*/
        //请求体定制：统一添加参数
        if (original.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) original.body();
            //把之前的参数添加进去
            for (int i = 0; i < oidFormBody.size(); i++) {
                newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                Log.e("参数", oidFormBody.encodedName(i) + "值：" + oidFormBody.encodedValue(i));
            }
            //统一用到的参数
            //   newFormBody.addEncoded("xn_data", json);

            requestBuilder.method(original.method(), newFormBody.build());
        } else if (original.body() instanceof MultipartBody) {

        } else if (original.body() != null && original.body().contentType() != null &&
                MediaType.parse("application/json").toString().equals(original.body().contentType().toString())) {

        } else if ("GET".equals(original.method())) {
        } else {
            FormBody.Builder newFormBody = new FormBody.Builder();
            //  newFormBody.addEncoded("xn_data", json);
            requestBuilder.method(original.method(), newFormBody.build());
        }

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

