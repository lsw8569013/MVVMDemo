package com.ironant.common.netutil;



import com.ironant.common.BuildConfig;
import com.ironant.common.netutil.interceptor.CommonRequestInterceptor;
import com.ironant.common.utils.LogUtil;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 适配多域名的api 实际请求的api  OCR
 * @author liushengwei
 * @description: https://github.com/lsw8569013
 * @date :2020-02-14 17:22
 */
public class OCRApi {

    private  String baseUrl;
    private String token;

    public volatile static Retrofit retrofit = null;

    private static OkHttpClient okHttpClient;
    private CommonRequestInterceptor interceptor;


    private static volatile OCRApi mInstance;

    public static OCRApi getInstance() {
        if (mInstance == null) {
            synchronized (OCRApi.class) {
                mInstance = new OCRApi();
            }
        }
        return mInstance;
    }

    protected OCRApi() {
//
        baseUrl = "http://www.etoplive.com/ocr/";
//        super("https://api.weixin.qq.com/");
//        super("http://47.105.206.32/ant/");
    }

    public static <T> T getService(Class<T> clazz) {
        return getInstance().getRetrofit().create(clazz);
    }




    private Retrofit getRetrofit() {
        if(retrofit == null){
            synchronized (Retrofit.class){
                if(retrofit == null){
                    retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkhttpClient())
                            .baseUrl(baseUrl)
                            .build();
            }
            }
        }
        return retrofit;
    }

    protected OkHttpClient getOkhttpClient() {
        if(okHttpClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.addInterceptor(interceptor);
            if(isDebug()){
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



    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
