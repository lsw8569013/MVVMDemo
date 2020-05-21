package com.lsw.mvvmdemo.http;


import com.ironant.common.callback.ACallback;
import com.ironant.common.utils.ToastUtils;


/**
 * author: Created by lsw on 2018/8/1 15:51
 * description:
 */
public abstract class NetCallBack<T> extends ACallback<T> {

    @Override
    public void onFail(int errCode, String errMsg) {

        ToastUtils.showShort(errMsg+"-"+errCode);
    }
}
