package com.example.mycloudmusic;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.mycloudmusic.util.ToastUtil;

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
        //初始化第三方toast框架
        Toasty.Config.getInstance().apply();
        //初始化toast工具类
        ToastUtil.init(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化MultiDex
        MultiDex.install(this);
    }
}
