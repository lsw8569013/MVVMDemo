package com.lsw.mvvmdemo;


import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.TextView;


import com.ironant.common.base.BaseActivity;
import com.lsw.mvvmdemo.databinding.ActivityLoginBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * 一个MVVM模式的登陆界面
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    private Disposable disposableCode;

    //ActivityLoginBinding类是databinding框架自定生成的,对应activity_login.xml
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        return createViewModel(this,LoginViewModel.class);
    }

    @Override
    protected void initData() {

        binding.etLoginname.setSingleLine();
        binding.tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime(binding.tvGetCode);
            }
        });

        viewModel.isShowPwd.observe(this, new androidx.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if(show){
//                    LogUtil.e("显示密码");
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());;
                }else{
//                    LogUtil.e("隐藏密码");
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        viewModel.isShowDialogData.observe(this, new androidx.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showD) {
                if(!showD){
//                    LogUtil.e("title == null");
                    dismissDialog();
                }else{
//                    LogUtil.e("title != null");
                    showDialog("登陆");
                }
            }
        });

    }

    /**
     * 开始计时
     */
    public  void startTime(final TextView tvGetCode) {
        final long codeTimes = 60;
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(codeTimes - 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return codeTimes - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {

                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        LoginActivity.this.disposableCode = disposable;
                        tvGetCode.setEnabled(false);
                    }
                })
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {
                        tvGetCode.setText("剩余" + value + "秒");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        tvGetCode.setEnabled(true);
                        tvGetCode.setText("获取验证码");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposableCode != null){
            disposableCode.dispose();
        }

    }
}
