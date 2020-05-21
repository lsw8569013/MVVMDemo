package com.ironant.common.base;

import android.app.Application;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;


import com.ironant.common.utils.DialogUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;



/**
 *
 */
public class BaseViewModel<M> extends AndroidViewModel implements LifecycleObserver {
    protected M model;
    protected Context ctx;

    //显示网络请求dialog
    public MutableLiveData<Boolean> isShowDialogData = new MutableLiveData<>(false);

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.ctx = application;
    }


    public void showDialog() {
        showDialog("请稍后...");
    }

    public void showDialog(String title) {
        isShowDialogData.setValue(true);
    }

    public void dismissDialog() {
        isShowDialogData.setValue(false);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent i = new Intent(ctx,clz);
        i.putExtra("params",bundle);
        ctx.startActivity(i);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    public void startContainerActivity(String canonicalName) {
        startContainerActivity(canonicalName, null);
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    public void startContainerActivity(String canonicalName, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CANONICAL_NAME, canonicalName);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }

    }

    /**
     * 关闭界面
     */
    public void finish() {

    }


    @Override
    protected void onCleared() {
        super.onCleared();
//        if (model != null) {
//            model.onCleared();
//        }
        //ViewModel销毁时会执行，同时取消所有异步任务

    }


    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }
}
