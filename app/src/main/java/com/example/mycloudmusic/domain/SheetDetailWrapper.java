package com.example.mycloudmusic.domain;

/**
 * 歌单详情模型
 *
 * 只是用来测试
 */
public class SheetDetailWrapper {
    /**
     * 歌单对象
     */
    private Sheet data;

    public Sheet getData() {
        return data;
    }

    public SheetDetailWrapper setData(Sheet data) {
        this.data = data;
        return this;
    }
}
