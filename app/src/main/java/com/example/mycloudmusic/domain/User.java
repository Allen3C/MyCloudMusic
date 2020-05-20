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
}
