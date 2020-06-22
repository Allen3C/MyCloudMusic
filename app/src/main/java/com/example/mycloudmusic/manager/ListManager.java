package com.example.mycloudmusic.manager;

import com.example.mycloudmusic.domain.Song;

import java.util.List;

/**
 * 列表管理器
 * 封装了列表相关的操作
 * 例如：上一曲、下一曲、循环模式
 */
public interface ListManager {

    /**
     * 设置播放列表
     * @param datum
     */
    void setDatum(List<Song> datum);

    /**
     * 获取播放列表
     * @return
     */
    List<Song> getDatum();

    /**
     * 播放
     * @param data
     */
    void play(Song data);

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续播放
     */
    void resume();

    /**
     * 上一曲
     * @return
     */
    Song previous();

    /**
     * 下一曲
     * @return
     */
    Song next();

    /**
     * 更改循环模式
     * @return
     */
    int changeLoopModel();

    /**
     * 获取循环模式
     * @return
     */
    int getLoopModel();
}
