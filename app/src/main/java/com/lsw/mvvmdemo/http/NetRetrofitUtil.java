package com.lsw.mvvmdemo.http;


import com.ironant.common.netutil.CommonNetApi;
import com.ironant.common.subscriber.ApiCallbackSubscriber;
import com.ironant.common.subscriber.ApiTransformer;
import com.lsw.mvvmdemo.bean.WeatherBean;

/**
 * 网络请求工具类 各种接口定义一个方法 在应用层关心网络请求的返回
 *
 * @author liushengwei
 * @description: https://github.com/lsw8569013
 * @date :2020-02-14 14:57
 */
public class NetRetrofitUtil {

    public static void getWeather(String version, ApiCallbackSubscriber apiCallbackSubscriber) {
        CommonNetApi.
                getService(HttpServiceHelp.getWeatherRX.class)
                .getWeather(version,"23035354","8YvlPNrz")
                .compose(ApiTransformer.<WeatherBean>norTransformer())
                .subscribe(apiCallbackSubscriber);

    }
}
