package com.example.mycloudmusic.listener;

import android.media.MediaPlayer;

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

    /**
     * 播放器准备完毕
     * @param mp
     * @param data
     */
    void onPrepared(MediaPlayer mp, Song data);
}
