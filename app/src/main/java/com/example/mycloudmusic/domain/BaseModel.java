package com.example.mycloudmusic.domain;

import java.io.Serializable;

/**
 * 所有模型父类
 */
public class BaseModel implements Serializable {

    /**
     * Id
     */
    private String id;

    /**
     * 创建时间
     */
    private String created_at;

    /**
     * 更新时间
     */
    private String updated_at;

    public String getId() {
        return id;
    }

    public BaseModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getCreated_at() {
        return created_at;
    }

    public BaseModel setCreated_at(String created_at) {
        this.created_at = created_at;
        return this;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public BaseModel setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
        return this;
    }
}
