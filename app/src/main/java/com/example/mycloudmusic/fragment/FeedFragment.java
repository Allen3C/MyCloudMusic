package com.example.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mycloudmusic.R;

/**
 * 首页-“动态”界面
 */
public class FeedFragment extends BaseCommonFragment {
    public static FeedFragment newInstance() {
        Bundle args = new Bundle();
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, null);
    }
}
