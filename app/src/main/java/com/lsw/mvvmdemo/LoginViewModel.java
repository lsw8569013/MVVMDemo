package com.lsw.mvvmdemo;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;

import com.ironant.common.base.BaseViewModel;
import com.ironant.common.subscriber.ApiCallbackSubscriber;
import com.ironant.common.utils.LogUtil;
import com.ironant.common.utils.ToastUtils;
import com.lsw.mvvmdemo.bean.WeatherBean;
import com.lsw.mvvmdemo.http.NetCallBack;
import com.lsw.mvvmdemo.http.NetRetrofitUtil;


/**
 * Created by goldze on 2017/7/17.
 */

public class LoginViewModel extends BaseViewModel {


    //用户名的绑定
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();



    //显示密码
    public MutableLiveData<Boolean> isShowPwd = new MutableLiveData<>(false);

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }


    public void showDialog(String title) {
        isShowDialogData.setValue(true);
    }

    public void dismissDialog() {
        isShowDialogData.setValue(false);
    }

    /**
     * 网络模拟一个登陆操作
     **/
    public void login() {
        LogUtil.e("login --");
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        showDialog("登陆中");
        ToastUtils.showShort("login");
        NetRetrofitUtil.getWeather("101030100",new ApiCallbackSubscriber(this,new NetCallBack<WeatherBean>() {
            @Override
            public void onSuccess(WeatherBean data) {
                LogUtil.e("请求成功！！");
                dismissDialog();
                LogUtil.e(data.getDate());
            }
        }));

    }

    public void clearUserName(){
        userName.set("");
    }

    public void showPwd(){
        LogUtil.e("showpwd");
        isShowPwd.setValue(isShowPwd.getValue() == null || !isShowPwd.getValue() );
    }

}
