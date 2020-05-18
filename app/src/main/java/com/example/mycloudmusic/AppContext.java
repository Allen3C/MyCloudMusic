package com.example.mycloudmusic;

import android.app.Application;

import es.dmoral.toasty.Toasty;

/**
 * 全局Application
 */
public class AppContext extends Application {
    /**
     * 创建Application时
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化toast框架
        Toasty.Config.getInstance().apply();
    }
}
