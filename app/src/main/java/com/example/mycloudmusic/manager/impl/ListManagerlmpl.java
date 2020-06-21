package com.example.mycloudmusic.manager.impl;

import android.content.Context;

import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.manager.ListManager;
import com.example.mycloudmusic.util.LogUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 列表接口默认实现类
 */
public class ListManagerlmpl implements ListManager {

    private static final String TAG = "ListManagerlmpl";
    private static ListManagerlmpl instance;
    private final Context context;

    /**
     * 播放列表
     */
    private List<Song> datum = new LinkedList<>();

    private ListManagerlmpl(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * 获取单例对象
     *
     * @param context
     * @return
     */
    public static synchronized ListManagerlmpl getInstance(Context context) {
        if (instance == null) {
            //只有没有初始化才创建对象
            instance = new ListManagerlmpl(context);
        }
        return instance;
    }

    @Override
    public void setDatum(List<Song> datum) {
        LogUtil.d(TAG,"setDatum");
    }

    @Override
    public List<Song> getDatum() {
        LogUtil.d(TAG,"getDatum");
        return datum;
    }

    @Override
    public void play(Song data) {
        LogUtil.d(TAG,"play");
    }

    @Override
    public void pause() {
        LogUtil.d(TAG,"pause");
    }

    @Override
    public void resume() {
        LogUtil.d(TAG,"resume");
    }
}
