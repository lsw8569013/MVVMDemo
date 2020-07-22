package com.ironant.common.netutil.interceptor;


import android.util.Log;

import com.ironant.common.BuildConfig;
import com.ironant.common.netutil.base.INetWorkInfo;
import com.ironant.common.utils.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author liushengwei 请求拦截器
 * 统一配置token 信息
 * @description: https://github.com/lsw8569013
 * @date :2020-02-14 14:03
 */
public class CommonRequestInterceptor implements Interceptor {

    private INetWorkInfo netWorkInfo;

    public  CommonRequestInterceptor(INetWorkInfo netWorkInfo) {
        this.netWorkInfo = netWorkInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        // 可以添加公共的请求投 token Access_Token
        String token = netWorkInfo.getToken();
        Request request = null;
        if (token != null) {
            request = builder.addHeader("Access_Token", token)
                    .build();
            LogUtil.i("token ==   " +token);
        }else{
            request = builder .build();
        }
        //打印 请求的URL 等信息
        Response response = chain.proceed(request);
        if (BuildConfig.DEBUG) {
            Log.e("lsw", "response返回参数" + response.toString());

        }

        //打印 服务器返回的信息
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        BufferedSource source = responseBody.source();
        source.request(Integer.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        String backSource = buffer.clone().readString(Charset.forName("UTF-8"));
        if(BuildConfig.DEBUG){
            if (contentLength != 0) {
                Log.e("lsw", "服务器返回数据 =：" + backSource);
            }
        }

        // 返回数据过滤事例 去除返回数据中 “”字段
        /*BaseBeanCheck bean = new Gson().fromJson(backSource, BaseBeanCheck.class);
        if(bean.getData() instanceof String){
            if(TextUtils.isEmpty((String) bean.getData())){
                bean.setData(null);
                String replace = new Gson().toJson(bean);
                if(BuildConfig.DEBUG){
                    Log.e("lsw", "服务器修改后 返回数据 =：" + replace);
                }
                Response build = response.newBuilder().body(ResponseBody.create(responseBody.contentType(), replace)).build();
                return build;
            }

        }*/
        return chain.proceed(builder.build());
    }
}
