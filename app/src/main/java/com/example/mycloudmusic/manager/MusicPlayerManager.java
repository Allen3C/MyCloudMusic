package com.example.mycloudmusic.manager;

import com.example.mycloudmusic.domain.Song;

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
}
