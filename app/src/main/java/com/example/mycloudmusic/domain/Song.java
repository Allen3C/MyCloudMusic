package com.example.mycloudmusic.domain;

import static com.example.mycloudmusic.util.Constant.TYPE_SONG;

/**
 * 单曲
 */
public class Song extends BaseMultiItemEntity {

    /**
     * 标题
     */
    private String title;

    /**
     * 封面
     */
    private String banner;

    /**
     * 音乐地址
     */
    private String uri;

    /**
     * 点击数
     */
    private int clicks_count;

    /**
     * 评论数
     */
    private int comments_count;

    /**
     * 歌词类型
     */
    private Integer style;

    /**
     * 歌词内容
     */
    private String lyric;

    /**
     * 创建音乐的用户
     */
    private User user;

    /**
     * 歌手
     */
    private User singer;

    //只有播放后才有值
    /**
     * 播放总进度
     * 单位：ms
     */
    private long duration;

    /**
     * 播放进度
     */
    private long progress;
    //end 只有播放后才有值

    public String getTitle() {
        return title;
    }

    public Song setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBanner() {
        return banner;
    }

    public Song setBanner(String banner) {
        this.banner = banner;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public Song setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public int getClicks_count() {
        return clicks_count;
    }

    public Song setClicks_count(int clicks_count) {
        this.clicks_count = clicks_count;
        return this;
    }

    public int getComments_count() {
        return comments_count;
    }

    public Song setComments_count(int comments_count) {
        this.comments_count = comments_count;
        return this;
    }

    public Integer getStyle() {
        return style;
    }

    public Song setStyle(Integer style) {
        this.style = style;
        return this;
    }

    public String getLyric() {
        return lyric;
    }

    public Song setLyric(String lyric) {
        this.lyric = lyric;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Song setUser(User user) {
        this.user = user;
        return this;
    }

    public User getSinger() {
        return singer;
    }

    public Song setSinger(User singer) {
        this.singer = singer;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    /**
     * 返回类型
     * @return
     */
    @Override
    public int getItemType() {
        return TYPE_SONG;
    }
}
