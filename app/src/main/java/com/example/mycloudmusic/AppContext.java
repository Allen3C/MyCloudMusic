package com.example.mycloudmusic;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.example.mycloudmusic.domain.Session;
import com.example.mycloudmusic.util.PreferenceUtil;
import com.example.mycloudmusic.util.ToastUtil;
import com.facebook.stetho.Stetho;

import es.dmoral.toasty.Toasty;

/**
 * 全局Application
 */
public class AppContext extends Application {


    private static AppContext context;

    /**
     * 创建Application时
     */
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        //初始化第三方toast框架
        Toasty.Config.getInstance().apply();
        //初始化toast工具类
        ToastUtil.init(getApplicationContext());

        //初始化Stetho抓包
        //使用默认参数初始化
        Stetho.initializeWithDefaults(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化MultiDex
        MultiDex.install(this);
    }

    /**
     * 获取全局Context
     * @return
     */
    public static AppContext getInstance() {
        return context;
    }

    /**
     * 当用户登录了
     */
    public void login(PreferenceUtil sp, Session data){
        //保存登录后的Session
        sp.setSession(data.getSession());
        //保存用户id
        sp.setUserId(data.getUser());
        //初始化其他登录后才需要初始化的内容
        onLogin();
    }

    /**
     * 初始化其他登录后才需要初始化的内容
     */
    private void onLogin() {

    }
}
