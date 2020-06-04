package com.example.mycloudmusic.domain;

import com.example.mycloudmusic.adapter.DiscoveryAdapter;

import static com.example.mycloudmusic.util.Constant.TYPE_TITLE;

public class Title extends BaseMultiItemEntity {

    /**
     * 标题
     */
    private String title;

    public String getTitle() {
        return title;
    }

    public Title setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public int getItemType() {
        return TYPE_TITLE;
    }


}
