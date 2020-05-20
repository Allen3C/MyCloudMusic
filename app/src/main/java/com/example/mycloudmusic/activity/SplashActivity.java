package com.example.mycloudmusic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.example.mycloudmusic.MainActivity;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.LogUtil;

/**
 * 启动界面
 */
public class SplashActivity extends BaseCommonActivity {
    private static final String TAG = "SplashActivity";
    private static final int MESSAGE_NEXT = 100;
    /**
     * 默认延时时间，单位毫秒
     */
    private static final long DEFAULT_DELAY_TIME = 3000;
    /**
     * 创建Handler
     * 这样创建有内存泄漏，这个内存泄漏先不解决，不影响
     */
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MESSAGE_NEXT:
                    next();
                    break;
            }
        }
    };

    private void next() {
//        startActivityAfterFinnishThis(GuideActivity.class);
        if(sp.isShowGuide()){
            //跳转引导界面
            startActivityAfterFinnishThis(GuideActivity.class);
        }else if(sp.isLogin()){
            //已经登录了 跳转到首页
            startActivityAfterFinnishThis(MainActivity.class);
        }else{
            //跳转登录注册界面
            startActivityAfterFinnishThis(LoginOrRegisterActivity.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置界面全屏
        fullScreen();

        //延时3秒
        //在企业中通常会有很多逻辑处理
        //所以延时时间最好是用3-消耗的的时间，比如网络请求或更新啥的消耗1秒，那就能保证启动界面总是3秒
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //3秒后执行
                handler.sendEmptyMessage(MESSAGE_NEXT);
            }
        }, DEFAULT_DELAY_TIME);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //测试productFlavors
        //获取ENDPOINT常量
        LogUtil.d(TAG, "initDatum:" + Constant.ENDPOINT);
    }
}
