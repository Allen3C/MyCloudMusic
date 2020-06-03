package com.example.mycloudmusic.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mycloudmusic.fragment.DiscoveryFragment;
import com.example.mycloudmusic.fragment.FeedFragment;
import com.example.mycloudmusic.fragment.MeFragment;
import com.example.mycloudmusic.fragment.VideoFragment;

/**
 * 主界面ViewPager的Adapter
 */
public class MainAdpter extends BaseFragmentPagerAdapter<Integer> {
    /**
     * 构造方法
     *
     * @param context 上下文
     * @param fm      Fragment管理器
     */
    public MainAdpter(Context context, @NonNull FragmentManager fm) {
        super(context, fm);
    }

    /**
     * 返回当前位置的Fragment
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return MeFragment.newInstance();
            case 1:
                return DiscoveryFragment.newInstance();
            case 2:
                return FeedFragment.newInstance();
            default:
                return VideoFragment.newInstance();
        }
    }
}
