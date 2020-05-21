package com.ironant.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ironant.common.utils.DialogUtils;
import com.ironant.common.utils.NetUtils;


/**
 * Created by lsw on 2017/11/15.
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mContext;

    protected View mView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mContext = (Activity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);

        createPresenter();



        initData();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mView != null) {
            mView = null;
        }
        if (mContext != null) {
            mContext = null;
        }
    }

    public abstract int getLayoutId();



    public boolean CheckNet(Context context){
        if(!NetUtils.isNetworkAvailable(context)){
            DialogUtils.makeText("您当前没有网络！");
            return false;
        }
        return true;
    }

    public abstract void initData();

    public abstract void createPresenter();

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public View findView(int id){
        return  mView.findViewById(id);
    }

}
