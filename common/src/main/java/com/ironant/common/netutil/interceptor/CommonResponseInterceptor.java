package com.ironant.common.netutil.interceptor;



import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * @author liushengwei 请求返回结果的拦截器
 * @description: https://github.com/lsw8569013
 * @date :2020-02-14 14:03
 */
public class CommonResponseInterceptor implements Interceptor {

    private static final String TAG = "ResponseInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();

        Response response = chain.proceed(chain.request());
        Log.d(TAG,"requestTime --> "+(System.currentTimeMillis() - requestTime));
        return response;
    }
}
