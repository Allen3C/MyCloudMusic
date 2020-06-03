package com.example.mycloudmusic.fragment;

import android.content.Context;
import android.content.Intent;

import com.example.mycloudmusic.activity.BaseCommonActivity;
import com.example.mycloudmusic.util.PreferenceUtil;

import butterknife.ButterKnife;

/**
 * 通用公共Fragment
 */
public abstract class BaseCommonFragment extends BaseFragment {

    protected PreferenceUtil sp;

    @Override
    protected void initViews() {
        super.initViews();
        if(isBindView()){
            bindView();
        }
    }

    protected void bindView() {
        ButterKnife.bind(this, getView());
    }

    protected boolean isBindView() {
        return true;
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //初始化偏好设置工具类
        sp = PreferenceUtil.getInstance(getMainActivity());
    }
    /**
     * 启动界面
     * @param clazz
     */
    protected void startActivity(Class<?> clazz){
        Intent intent = new Intent(getMainActivity(), clazz);
        startActivity(intent);
    }
    /**
     * 启动界面并关闭当前界面
     * @param clazz
     */
    protected void startActivityAfterFinnishThis(Class<?> clazz){
        startActivity(clazz);
        //关闭当前界面，就是跳转之后按导航栏的返回回不到当前界面了
        getMainActivity().finish();
    }
    /**
     * 获取界面
     * @return
     */
    public BaseCommonActivity getMainActivity(){
        return (BaseCommonActivity) getActivity();
    }
}
