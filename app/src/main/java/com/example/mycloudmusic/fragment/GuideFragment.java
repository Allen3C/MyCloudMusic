package com.example.mycloudmusic.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.Constant;

/**
 * 引导界面Fragment
 */
public class GuideFragment extends BaseCommonFragment {

    private ImageView iv;

    public GuideFragment() {
    }
    /**
     * 创建单例
     * @return
     */
    public static GuideFragment newInstance(int id) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 加载布局，返回要显示的view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //找控件
        iv = findViewById(R.id.iv);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //取出传递的数据
        int id = getArguments().getInt(Constant.ID, -1);
        //判断数据格式
        if(id == -1){
            //如果图片不存在
            //就关闭当前界面
            //但在我们这里不会发生该情况
            getActivity().finish();
            return;
        }
        iv.setImageResource(id);
    }
}
