package com.ironant.common.subscriber;


import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.ironant.common.base.BaseViewModel;
import com.ironant.common.callback.ACallback;
import com.ironant.common.exception.ApiException;
import com.ironant.common.netutil.IBaseLifeObserver;
import com.ironant.common.utils.LogUtil;


/**
 * @Description: 包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 * @author:
 * @date: 2017-01-05 09:35
 */
public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> implements IBaseLifeObserver {

    private  BaseViewModel viewModel;
    ACallback<T> callBack;
    T data;

    public ApiCallbackSubscriber(ACallback<T> callBack) {
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    public ApiCallbackSubscriber(BaseViewModel viewModel, ACallback<T> callBack) {
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
        this.viewModel = viewModel;
    }

    @Override
    public void onError(ApiException e) {
        // error 的时候，自动取消dialog
        if(viewModel !=null){
            viewModel.dismissDialog();
        }
        if (e == null) {
            callBack.onFail(-1, "This ApiException is Null.");
            return;
        }
        callBack.onFail(e.getCode(), e.getMessage());
    }

    @Override
    public void onNext(T t) {
        this.data = t;
        try {
            callBack.onSuccess(t);
        }catch (Exception e){
            Log.e("http ApiCallback","http OK ，UI   --- crash ");
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
    }

    public T getData() {
        return data;
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {
        LogUtil.e("界面 onCreate ");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {
        LogUtil.e("界面 onStart ");
    }

    @Override
    public void onStop() {
        LogUtil.e("界面 onStop http dispose");
        dispose();
    }

    @Override
    public void onResume() {
        LogUtil.e("界面 onResume ");
    }

    @Override
    public void onPause() {
        LogUtil.e("界面 onPause ");
    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }
}
