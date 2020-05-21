package com.ironant.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;

/**
 * 网络工具类
 * Created By: AndroidStudio [FR]
 * Author : Admin [FR]
 * Date :  2016/8/11
 */
public class NetUtils {

    public static enum NetType {
        WIFI, CMNET, CMWAP, NONE
    }

    /**
     * 当前的网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断网络连接是否打开
     */
    public static boolean isNetworkConnected(Context ctx) {
        boolean flag = false;
        ConnectivityManager netManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (netManager.getActiveNetworkInfo() != null) {
            flag = netManager.getActiveNetworkInfo().isAvailable();
        }
        return flag;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取运营商的网络类型
     * @param context
     * @return
     */
    public static NetType getAPNType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }
        int nType = networkInfo.getType();

        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

}

