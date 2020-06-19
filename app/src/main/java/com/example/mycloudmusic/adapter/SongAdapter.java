package com.example.mycloudmusic.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mycloudmusic.domain.Song;

/**
 * 歌单详情-歌曲适配器
 */
public class SongAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId 布局Id
     */
    public SongAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 显示数据
     *
     * @param helper
     * @param data
     */
    @Override
    protected void convert(BaseViewHolder helper, Song data) {

    }

}

