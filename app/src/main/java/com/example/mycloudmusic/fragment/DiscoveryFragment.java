package com.example.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloudmusic.R;

import butterknife.BindView;

/**
 * 首页-“发现”界面
 */
public class DiscoveryFragment extends BaseCommonFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    private GridLayoutManager layoutManager;

    @Override
    protected void initViews() {
        super.initViews();

        //高度固定
        rv.setHasFixedSize(true);

        //设置显示3列
        layoutManager = new GridLayoutManager(getMainActivity(), 3);
        rv.setLayoutManager(layoutManager);
    }

    public static DiscoveryFragment newInstance() {

        Bundle args = new Bundle();

        DiscoveryFragment fragment = new DiscoveryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected View getLayoutView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discovery, null);
    }
}
