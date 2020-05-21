package com.lsw.mvvmdemo.app;


import android.os.Build;
import android.os.StrictMode;

import com.ironant.common.base.BaseApplication;
import com.ironant.common.utils.Utils;


/**
 * Created by lsw
 */

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }


}
