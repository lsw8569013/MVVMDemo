package com.ironant.common.netutil;



import com.ironant.common.BuildConfig;
import com.ironant.common.netutil.base.NetWorkApi;
import com.ironant.common.netutil.interceptor.CommonResponseInterceptor;

import okhttp3.Interceptor;

/**
 * 适配多域名的api 实际请求的api
 * @author liushengwei
 * @description: https://github.com/lsw8569013
 *
 * @date :2020-02-14 17:22
 *
 * 设置token 事例代码
 * CommonNetApi.getInstance().setToken(token);
 *
 */
public class CommonNetApi extends NetWorkApi {

    /**
     * 统一配置 token
     */
    private String token;
    private static volatile CommonNetApi mInstance;

    public static CommonNetApi getInstance() {
        if (mInstance == null) {
            synchronized (CommonNetApi.class) {
                mInstance = new CommonNetApi();
            }
        }
        return mInstance;
    }

    /**
     * baseUrl must end in /
     */
    protected CommonNetApi() {
        super("http://www.tianqiapi.com/");
    }

    public static <T> T getService(Class<T> clazz) {
        return getInstance().getRetrofit().create(clazz);
    }

    @Override
    protected Interceptor getInterceptor() {
        return new CommonResponseInterceptor();
    }

    @Override
    public String getAppVersionName() {
        return null;
    }

    @Override
    public String getAPPVersionCode() {
        return null;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }


    @Override
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }


}
