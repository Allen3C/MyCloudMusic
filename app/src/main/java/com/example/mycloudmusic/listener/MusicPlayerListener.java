package com.example.mycloudmusic.listener;

import com.example.mycloudmusic.domain.Song;

/**
 * 播放器接口
 */
public interface MusicPlayerListener {
    /**
     * 已经暂停了
     */
    void onPaused(Song data);

    /**
     * 已经播放了
     */
    void onPlaying(Song data);

}
