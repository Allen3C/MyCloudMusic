package com.example.mycloudmusic.manager;

import com.example.mycloudmusic.activity.SimplePlayerActivity;
import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.listener.MusicPlayerListener;

/**
 * 音乐播放器对外暴露的接口
 */
public interface MusicPlayerManager {
    /**
     * 播放音乐
     * @param uri 播放音乐的绝对地址
     * @param data
     */
    void play(String uri, Song data);

    /**
     * 是否在播放
     * @return
     */
    boolean isPlaying();

    /**
     * 暂停
     */
    void pause();

    /**
     *继续播放
     */
    void resume();

    /**
     * 添加播放监听器
     * @param listener
     */
    void addMusicPlayerListener(MusicPlayerListener listener);

    /**
     * 移除播放监听器
     * @param listener
     */
    void removeMusicPlayerListener(MusicPlayerListener listener);

    /**
     * 获取当前播放的音乐
     * @return
     */
    Song getData();

    /**
     * 从指定位置播放
     * @param progress
     */
    void seekTo(int progress);
}
