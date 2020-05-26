package com.example.mycloudmusic.util;

import android.os.Looper;

/**
 * Handler工具类
 */
public class HandlerUtil    {
    /**
     * 判断是否在主线程
     * @return
     */
    public static boolean isMainThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
