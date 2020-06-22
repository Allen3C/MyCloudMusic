package com.example.mycloudmusic.manager.impl;

import android.content.Context;

import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.manager.ListManager;
import com.example.mycloudmusic.manager.MusicPlayerManager;
import com.example.mycloudmusic.service.MusicPlayerService;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.ResourceUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.example.mycloudmusic.util.Constant.MODEL_LOOP_LIST;
import static com.example.mycloudmusic.util.Constant.MODEL_LOOP_ONE;
import static com.example.mycloudmusic.util.Constant.MODEL_LOOP_RANDOM;

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

    /**
     * 当前音乐对象
     */
    private Song data;
    private final MusicPlayerManager musicPlayerManager;
    /**
     * 是否播放了
     */
    private boolean isPlay;
    private int model = MODEL_LOOP_LIST;

    private ListManagerlmpl(Context context) {
        this.context = context.getApplicationContext();

        //初始化音乐播放管理器
        musicPlayerManager = MusicPlayerService.getMusicPlayerManager(this.context);
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
        //清空原来的数据
        this.datum.clear();

        //添加新的数据
        this.datum.addAll(datum);
    }

    @Override
    public List<Song> getDatum() {
        LogUtil.d(TAG,"getDatum");
        return datum;
    }

    @Override
    public void play(Song data) {
        LogUtil.d(TAG,"play");

        //标记已经播放了
        isPlay = true;

        //保存数据
        this.data = data;

        //播放音乐
        musicPlayerManager.play(ResourceUtil.resourceUri(data.getUri()), data);
    }

    @Override
    public void pause() {
        LogUtil.d(TAG,"pause");
        musicPlayerManager.pause();
    }

    @Override
    public void resume() {
        LogUtil.d(TAG,"resume");

        if (isPlay) {
            //原来已经播放过
            //也就说播放器已经初始化了
            musicPlayerManager.resume();
        } else {
            //到这里，是应用开启后，第一次点继续播放
            //而这时内部其实还没有准备播放，所以应该调用播放
            play(data);
        }
    }

    @Override
    public Song previous() {

        //音乐索引
        int index = 0;

        //判断循环模式
        switch (model) {
            case MODEL_LOOP_RANDOM:
                //随机循环

                //在0~datum.size()中
                //不包含datum.size()
                index = new Random().nextInt(datum.size());
                break;
            default:
                //找到当前音乐索引
                index = datum.indexOf(data);

                if (index != -1) {
                    //找到了

                    //如果当前播放是列表第一个
                    if (index == 0) {
                        //第一首音乐

                        //那就从最后开始播放
                        index = datum.size() - 1;
                    } else {
                        index--;
                    }
                } else {
                    //抛出异常
                    //因为正常情况下是能找到的
                    throw new IllegalArgumentException("Cant't find current song");
                }
                break;
        }

        //获取音乐
        return datum.get(index);
    }

    @Override
    public Song next() {
        if (datum.size() == 0) {
            //如果没有音乐了
            //直接返回null
            return null;
        }

        //音乐索引
        int index = 0;

        //判断循环模式
        switch (model) {
            case MODEL_LOOP_RANDOM:
                //随机循环

                //在0~datum.size()中
                //不包含datum.size()
                index = new Random().nextInt(datum.size());
                break;
            default:
                //找到当前音乐索引
                index = datum.indexOf(data);

                if (index != -1) {
                    //找到了

                    //如果当前播放是列表最后一个
                    if (index == datum.size() - 1) {
                        //最后一首音乐

                        //那就从0开始播放
                        index = 0;
                    } else {
                        index++;
                    }
                } else {
                    //抛出异常
                    //因为正常情况下是能找到的
                    throw new IllegalArgumentException("Cant'found current song");
                }
                break;
        }

        //获取音乐
        return datum.get(index);
    }

    @Override
    public int changeLoopModel() {

        //循环模式+1
        model++;

        //判断循环模式边界
        if (model > MODEL_LOOP_RANDOM) {
            //如果当前循环模式
            //大于最后一个循环模式
            //就设置为第0个循环模式
            model = MODEL_LOOP_LIST;
        }

        //判断是否是单曲循环
        if (MODEL_LOOP_ONE == model) {
            //单曲循环模式
            //设置到mediaPlay
            musicPlayerManager.setLooping(true);
        } else {
            //其他模式关闭循环
            musicPlayerManager.setLooping(false);
        }

        //返回最终的循环模式
        return model;
    }

    @Override
    public int getLoopModel() {
        return model;
    }
}
