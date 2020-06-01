package com.example.mycloudmusic;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

import com.example.mycloudmusic.activity.LoginOrRegisterActivity;
import com.example.mycloudmusic.domain.Session;
import com.example.mycloudmusic.domain.event.LoginSuccessEvent;
import com.example.mycloudmusic.util.PreferenceUtil;
import com.example.mycloudmusic.util.ToastUtil;
import com.facebook.stetho.Stetho;
import com.mob.MobSDK;

import org.greenrobot.eventbus.EventBus;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
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

        //初始化ShareSDK
        MobSDK.init(this);
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
        //发送登录成功通知
        //目的是一些界面需要接收该事件
        EventBus.getDefault().post(new LoginSuccessEvent());
    }

    /**
     * 初始化其他登录后才需要初始化的内容
     */
    private void onLogin() {

    }

    /**
     * 退出
     */
    public void logout() {

        //清除登录相关信息
        PreferenceUtil.getInstance(getApplicationContext()).logout();

        //qq退出
        otherLogout(QQ.NAME);
        //微博退出
        otherLogout(SinaWeibo.NAME);

        //退出后跳转到登录注册界面
        Intent intent = new Intent(getApplicationContext(), LoginOrRegisterActivity.class);

        //在Activity以外启动界面
        //都要写这个标识
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        //启动界面
        startActivity(intent);

        //退出了通知
        onLogout();
    }

    /**
     * 第三方平台退出
     * @param name
     */
    private void otherLogout(String name) {
        //清除第三方平台登录信息
        Platform platform = ShareSDK.getPlatform(name);
        if(platform.isAuthValid()){
            platform.removeAccount(true);
        }
    }

    /**
     * 退出了通知
     */
    private void onLogout() {

    }
}
