package com.ironant.common.base;


import android.content.Intent;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.afollestad.materialdialogs.MaterialDialog;
import com.ironant.common.utils.LogUtil;
import com.ironant.common.utils.MaterialDialogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by goldze on 2017/6/15.
 * 一个拥有DataBinding框架的基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity, 但是需要继承RxAppCompatActivity,方便LifecycleProvider管理生命周期
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity {
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private volatile MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面接受的参数方法

        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);


    }


    /**
     * 注入绑定
     */
    private void initViewDataBinding(Bundle savedInstanceState) {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = BaseViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        initData();
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);


        viewModel.isShowDialogData.observe(this, new androidx.lifecycle.Observer<Boolean>() {
            //
            @Override
            public void onChanged(Boolean showD) {
                if (!showD) {
                    dismissDialog();
                } else {
                    showDialog("登陆");
                }
            }
        });
    }

    protected abstract void initData();

    //刷新布局
    public void refreshLayout() {
        if (viewModel != null) {
            binding.setVariable(viewModelId, viewModel);
        }
    }


    public synchronized void showDialog(String title) {
        if (dialog != null) {
            dialog = dialog.getBuilder().title(title).build();
            if (!dialog.isShowing()) {
//                LogUtil.e("dialog 没有显示");
                dialog.show();
            } else {
//                LogUtil.e("dialog 显示");
            }
        } else {
//            LogUtil.e("dialog 没有隐藏");
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(this, title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除Messenger注册
//        Messenger.getDefault().unregister(viewModel);

        if (binding != null) {
            binding.unbind();
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
//    public void startContainerActivity(String canonicalName, Bundle bundle) {
//        Intent intent = new Intent(this, ContainerActivity.class);
//        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName);
//        if (bundle != null) {
//            intent.putExtra(ContainerActivity.BUNDLE, bundle);
//        }
//        startActivity(intent);
//    }


    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    public abstract int initContentView(Bundle savedInstanceState);

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    public abstract int initVariableId();

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(activity.getApplication())).get(cls);
    }


//

}
