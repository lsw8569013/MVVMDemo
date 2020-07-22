package com.ironant.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ironant.common.utils.MaterialDialogUtils;
import com.ironant.common.utils.SharedPreferencesUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public  abstract class BaseVmFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends Fragment /*implements CustomAdapt*/ {


    protected Context context;
    protected V binding;
    protected VM viewModel;
    private int viewModelId;
    private MaterialDialog dialog;
    protected SharedPreferencesUtils spUtils;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        spUtils = SharedPreferencesUtils.getInstance(getActivity().getApplicationContext());
        return binding.getRoot();
    }

    protected abstract int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding();

        //页面数据初始化方法
        initData();
        //页面事件监听的方法，一般用于ViewModel层转到View层的事件注册
        initViewObservable();

    }
    protected abstract void initData();

    protected abstract void initViewObservable();


    protected abstract int initVariableId();

    /**
     * 注入绑定
     */
    private void initViewDataBinding() {
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
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);

    }


    public synchronized void showDialog(String title) {
        if (dialog != null) {
            dialog = dialog.getBuilder().title(title).build();
            if(!dialog.isShowing()){
//                LogUtil.e("dialog 没有显示");
                dialog.show();
            }else{
//                LogUtil.e("dialog 显示");
            }
        } else {
//            LogUtil.e("dialog 没有隐藏");
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(context, title, true);
            dialog = builder.show();
        }
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }



    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends BaseViewModel> T createViewModel(Fragment fragment, Class<T> cls) {
        return new ViewModelProvider(getActivity()).get(cls);
//        return new ViewModelProvider(this,
//                new ViewModelProvider.AndroidViewModelFactory(fragment.getActivity().getApplication())).get(cls);
//        return  new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(cls);
    }

    /**
     * finish 界面Acy
     */
    protected void finishAcy(){
        getActivity().finish();
    }


    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getContext(), clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }



    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public  VM initViewModel(){
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (binding != null) {
            binding.unbind();
        }
    }

    public int dpToPx(float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

}
