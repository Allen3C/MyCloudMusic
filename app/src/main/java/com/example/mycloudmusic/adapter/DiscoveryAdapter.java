package com.example.mycloudmusic.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.BaseMultiItemEntity;
import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.Title;
import com.example.mycloudmusic.util.ImageUtil;

import java.util.ArrayList;

import static com.example.mycloudmusic.util.Constant.TYPE_SHEET;
import static com.example.mycloudmusic.util.Constant.TYPE_SONG;
import static com.example.mycloudmusic.util.Constant.TYPE_TITLE;

/**
 * 发现界面适配器
 */
public class DiscoveryAdapter extends BaseMultiItemQuickAdapter<BaseMultiItemEntity, BaseViewHolder> {
    /**
     * 构造方法
     *
     */
    public DiscoveryAdapter() {
        //第一次他要传入数据
        //而这时候我们还没有准备好数据
        //所以传递一个空列表
        super(new ArrayList<>());

        //添加多类型布局

        //添加标题类型
        addItemType(TYPE_TITLE, R.layout.item_title);

        //添加歌单类型
        addItemType(TYPE_SHEET, R.layout.item_sheet);

        //添加单曲类型
        addItemType(TYPE_SONG, R.layout.item_song);
    }

    /**
     * 绑定数据方法
     *
     * 复用等步骤不用管
     * 框架内部自动处理
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(BaseViewHolder helper, BaseMultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_TITLE:
                //标题
                Title title = (Title) item;
                //设置标题
                helper.setText(R.id.tv_title, title.getTitle());
                break;
            case TYPE_SHEET:
                //歌单
                Sheet sheet = (Sheet) item;
                //显示图片
                ImageUtil.show((Activity) mContext, helper.getView(R.id.iv_banner), sheet.getBanner());
                //设置歌单标题
                helper.setText(R.id.tv_title, sheet.getTitle());
                //播放量
                helper.setText(R.id.tv_info, String.valueOf(sheet.getClicks_count()));
                break;
            case TYPE_SONG:
                //单曲

                break;
        }
    }
}
