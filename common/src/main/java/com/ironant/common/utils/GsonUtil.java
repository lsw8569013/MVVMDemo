package com.ironant.common.utils;

import com.google.gson.Gson;

public class GsonUtil {
    private volatile static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}