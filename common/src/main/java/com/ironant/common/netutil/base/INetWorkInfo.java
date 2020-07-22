package com.ironant.common.netutil.base;

/**
 * @author liushengwei
 * @description: https://github.com/lsw8569013
 * @date :2020-02-14 12:55
 */
public interface INetWorkInfo {
    String getAppVersionName();
    String getAPPVersionCode();
    boolean isDebug();
    String getToken();
    void setToken(String token);
}
