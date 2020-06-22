package com.example.mycloudmusic.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mycloudmusic.domain.Song;

/**
 * 简单播放界面列表适配器
 */
public class SimplePlayerAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public SimplePlayerAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 显示数据
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {
        //显示标题
        helper.setText(android.R.id.text1, item.getTitle());
    }
}
