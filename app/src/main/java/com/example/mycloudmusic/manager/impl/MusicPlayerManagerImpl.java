package com.example.mycloudmusic.manager.impl;

import android.content.Context;

import com.example.mycloudmusic.manager.MusicPlayerManager;

/**
 * 播放管理器默认实现
 */
public class MusicPlayerManagerImpl implements MusicPlayerManager {

    /**
     * 单例实例对象
     */
    private static MusicPlayerManagerImpl instance;
    /**
     * 上下文
     */
    private final Context context;

    /**
     * 构造方法
     * @param context
     */
    private MusicPlayerManagerImpl(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 获取单例对象
     *
     * getInstance：方法名可以随便取
     * 只是在Java这边大部分项目都取这个名字
     * @param context
     * @return
     */
    public static synchronized MusicPlayerManager getInstance(Context context) {
        if (instance == null) {
            //如果为空
            //就创建实现类
            instance = new MusicPlayerManagerImpl(context);
        }

        //返回对象
        return instance;
    }
}
