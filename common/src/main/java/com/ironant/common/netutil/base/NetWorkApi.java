package com.ironant.common.netutil.base;



import com.ironant.common.netutil.interceptor.CommonRequestInterceptor;
import com.ironant.common.utils.LogUtil;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author liushengwei
 * @description: https://github.com/lsw8569013
 * @date :2020-02-14 12:55
 */
public abstract class NetWorkApi implements INetWorkInfo {

    private static String baseUrl ;

    private static final String BASE_URL = "http://t.weather.sojson.com/api/weather/";
    public volatile static Retrofit retrofit = null;
    public static INetWorkInfo netWorkInfo ;
    private static OkHttpClient okHttpClient;

    protected NetWorkApi(String baseUrl){
        this.baseUrl = baseUrl;
        init(this);
    }

    public static void init(INetWorkInfo mINetworkInfo){
        netWorkInfo = mINetworkInfo;
    }

    protected    Retrofit getRetrofit() {
        if(retrofit == null){
            synchronized (Retrofit.class){
                if(retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(baseUrl)
                            .client(getOkhttpClient())
                            .build();
                }
            }
        }
        return retrofit;
    }




    protected  OkHttpClient getOkhttpClient() {
        if(okHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new CommonRequestInterceptor(netWorkInfo));
            builder.addInterceptor(getInterceptor());
            if(netWorkInfo != null && netWorkInfo.isDebug()){
                HttpLoggingInterceptor httpLogInterceptor =  new HttpLoggingInterceptor();
                httpLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addNetworkInterceptor(httpLogInterceptor);
                LogUtil.i("netWorkInfo 设置 log 打印");
            }else{
                LogUtil.i("netWorkInfo 不设置 log 打印");
            }
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    protected abstract Interceptor getInterceptor();
}
