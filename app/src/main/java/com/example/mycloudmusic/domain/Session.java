package com.example.mycloudmusic.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 登录后模型
 */
public class Session {

    /**
     * 用户Id
     */
    private String user;

    /**
     * 登录后的Session
     */
    private String session;

    public String getUser() {
        return user;
    }

    public Session setUser(String user) {
        this.user = user;
        return this;
    }

    public String getSession() {
        return session;
    }

    public Session setSession(String session) {
        this.session = session;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("user", user)
                .append("session", session)
                .toString();
    }
}
