package com.example.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mycloudmusic.R;

/**
 * 首页-“朋友”界面
 */
public class FriendFragment extends BaseCommonFragment {
    public static FriendFragment newInstance() {
        Bundle args = new Bundle();
        FriendFragment fragment = new FriendFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friend, null);
    }
}
