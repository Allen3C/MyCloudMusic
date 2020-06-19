package com.example.mycloudmusic.domain;

/**
 * 广告模型
 */
public class Ad {
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String banner;
    /**
     * 点击广告后跳转的地址
     */
    private String uri;

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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
