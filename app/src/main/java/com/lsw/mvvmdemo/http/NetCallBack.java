package com.lsw.mvvmdemo.http;


import com.ironant.common.BuildConfig;
import com.ironant.common.callback.ACallback;
import com.ironant.common.utils.LogUtil;
import com.ironant.common.utils.ToastUtils;


/**
 * 网络请求回调
 * author: Created by lsw on 2018/8/1 15:51
 * description:
 */
public abstract class NetCallBack<T> extends ACallback<T> {

    /**
     * 异常信息提示
     * @param errCode
     * @param errMsg
     */
    @Override
    public void onFail(int errCode, String errMsg) {
        LogUtil.e(errMsg);
        ToastUtils.showShort(errMsg+"-"+errCode);
    }
}
