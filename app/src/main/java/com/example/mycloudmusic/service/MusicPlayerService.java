package com.example.mycloudmusic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.mycloudmusic.manager.MusicPlayerManager;
import com.example.mycloudmusic.manager.impl.MusicPlayerManagerImpl;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.ServiceUtil;

/**
 * 音乐播放service
 */
public class MusicPlayerService extends Service {

    private static final String TAG = "MusicPlayerService";

    /**
     * 构造方法
     */
    public MusicPlayerService(){

    }

    /**
     * 获取音乐播放Manager
     *
     * 为什么不支持将逻辑写到Service呢？
     * 是因为操作service要么通过bindService
     * 那么startService来使用
     * 比较麻烦
     * @param context
     * @return
     */
    public static MusicPlayerManager getMusicPlayerManager(Context context) {
        //尝试启动service
        ServiceUtil.startService(context,MusicPlayerService.class);
        return MusicPlayerManagerImpl.getInstance(context);
    }

    /**
     * Service创建了
     * 类似Activity的onCreate
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d(TAG, "onCreate");
    }

    /**
     * 启动service调用
     *
     * 多次启动也调用该方法
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG,"onDestroy");

        //停止前台服务
        //true表示是否移除之前的通知
        stopForeground(true);

        super.onDestroy();
    }
}