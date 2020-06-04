package com.example.mycloudmusic.domain;

import static com.example.mycloudmusic.util.Constant.TYPE_SONG;

/**
 * 单曲
 */
public class Song extends BaseMultiItemEntity {
    /**
     * 返回类型
     * @return
     */
    @Override
    public int getItemType() {
        return TYPE_SONG;
    }
}
