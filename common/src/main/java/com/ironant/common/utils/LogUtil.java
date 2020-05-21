package com.ironant.common.utils;

import android.util.Log;

import com.ironant.common.BuildConfig;


/**
 * Created by Administrator on 2017/11/7.
 */

public class LogUtil {


    public static void e(String msg){
        if(BuildConfig.DEBUG){
            Log.e("lsw",msg);
        }
    }
    public static void w(String msg){
        if(BuildConfig.DEBUG){
            Log.w("lsw",msg);
        }
    }

    public static void e(Exception e) {
        if(BuildConfig.DEBUG){
            Log.w("lsw",e.getMessage().toString());
        }
    }

    public static void i(String msg) {
        if(BuildConfig.DEBUG){
            Log.i("lsw",msg);
        }
    }
}
