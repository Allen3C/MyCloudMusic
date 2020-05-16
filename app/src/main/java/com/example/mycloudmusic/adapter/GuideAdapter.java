package com.example.mycloudmusic.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.fragment.GuideFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导界面适配器
 */
public class GuideAdapter extends BaseFragmentPagerAdapter<Integer> {
    /**
     * 列表数据源
     */
    protected List<Integer> datum = new ArrayList<>();

    public GuideAdapter(Context context, @NonNull FragmentManager fm) {
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
        return GuideFragment.newInstance(getData(position));
    }
}
