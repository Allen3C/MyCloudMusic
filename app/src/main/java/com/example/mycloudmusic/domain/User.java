package com.example.mycloudmusic.domain;

/**
 * 用户模型
 */
public class User extends BaseModel {

    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 用户的密码,登录，注册向服务端传递
     */
    private String password;
    /**
     * QQ第三方登录后Id
     */
    private String qq_id;

    /**
     * 微博第三方登录后Id
     */
    private String weibo_id;

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getQq_id() {
        return qq_id;
    }

    public User setQq_id(String qq_id) {
        this.qq_id = qq_id;
        return this;
    }

    public String getWeibo_id() {
        return weibo_id;
    }

    public User setWeibo_id(String weibo_id) {
        this.weibo_id = weibo_id;
        return this;
    }
}
