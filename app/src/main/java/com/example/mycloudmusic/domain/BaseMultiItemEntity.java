package com.example.mycloudmusic.domain;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 通用多类型模型
 */
public abstract class BaseMultiItemEntity extends BaseModel implements MultiItemEntity {

    /**
     * 占用多少列
     *
     * @return
     */
    public int getSpanSize() {
        return 3;
    }
}
