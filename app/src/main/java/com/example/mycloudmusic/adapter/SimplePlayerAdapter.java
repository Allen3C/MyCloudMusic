package com.example.mycloudmusic.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.Song;

/**
 * 简单播放界面列表适配器
 */
public class SimplePlayerAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    /**
     * 选中的歌曲索引
     */
    private int selectedIndex = -1;


    /**
     * 构造方法
     *
     * @param layoutResId
     */
    public SimplePlayerAdapter(int layoutResId) {
        super(layoutResId);
    }

    /**
     * 显示数据
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, Song item) {

        //获取到文本控件
        TextView tv_title = helper.getView(android.R.id.text1);

        //显示标题
        helper.setText(android.R.id.text1, item.getTitle());
        //tv_title.setText(item.getTitle());

        //处理选中状态
        if (selectedIndex == helper.getAdapterPosition()) {
            //选中行
            tv_title.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            //未选中
            tv_title.setTextColor(mContext.getResources().getColor(R.color.text));
        }
    }

    /**
     * 选中音乐
     *
     * @param index
     */
    public void setSelectedIndex(int index) {
        //先刷新上一行
        notifyItemChanged(this.selectedIndex);

        //保存选中索引
        this.selectedIndex = index;

        //刷新当前行
        notifyItemChanged(this.selectedIndex);

    }
}
