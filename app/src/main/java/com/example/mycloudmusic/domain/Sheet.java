package com.example.mycloudmusic.domain;

import static com.example.mycloudmusic.util.Constant.TYPE_SHEET;

/**
 * 歌单对象
 */
public class Sheet extends BaseMultiItemEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String banner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    /**
     * 使用了BaseRecyclerViewAdapterHelper框架
     * 实现多类型1列表
     * 需要实现该接口返回类型
     * @return
     */
    @Override
    public int getItemType() {
        return TYPE_SHEET;
    }

    @Override
    public int getSpanSize() {
        return 1;
    }
}
