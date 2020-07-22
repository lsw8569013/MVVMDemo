package com.ironant.common.callback;

/**
 * @Description: 请求接口回调抽象类
 * @author: lsw8569013
 * @date: 17/5/15 10:54.
 */
public abstract class ACallback<T> {
    public abstract void onSuccessParent(T data);
    public abstract void onFail(int errCode, String errMsg);

    /**
     * 请求成功和失败都会回调这个方法，用于dialog dismiss 调用
     */
    public abstract void onComplete();
}
