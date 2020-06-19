package com.example.mycloudmusic.domain;

import java.util.List;

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

    /**
     * 描述
     */
    private String description;

    /**
     * 点击数
     */
    private int clicks_count;

    /**
     * 收藏数
     */
    private int collections_count;

    /**
     * 评论数
     */
    private int comments_count;

    /**
     * 音乐数量
     */
    private int songs_count;

    /**
     * 歌单创建者
     */
    private User user;

    /**
     * 歌曲
     */
    private List<Song> songs;

    /**
     * 是否收藏
     * 有值就表示收藏了
     */
    private Integer collection_id;


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

    public Sheet setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getClicks_count() {
        return clicks_count;
    }

    public Sheet setClicks_count(int clicks_count) {
        this.clicks_count = clicks_count;
        return this;
    }

    public int getCollections_count() {
        return collections_count;
    }

    public Sheet setCollections_count(int collections_count) {
        this.collections_count = collections_count;
        return this;
    }

    public int getComments_count() {
        return comments_count;
    }

    public Sheet setComments_count(int comments_count) {
        this.comments_count = comments_count;
        return this;
    }

    public int getSongs_count() {
        return songs_count;
    }

    public Sheet setSongs_count(int songs_count) {
        this.songs_count = songs_count;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Sheet setUser(User user) {
        this.user = user;
        return this;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public Sheet setSongs(List<Song> songs) {
        this.songs = songs;
        return this;
    }

    public Integer getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(Integer collection_id) {
        this.collection_id = collection_id;
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

    public String getDescription() {
        return description;
    }
}
