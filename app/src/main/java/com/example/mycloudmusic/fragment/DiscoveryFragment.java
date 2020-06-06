package com.example.mycloudmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.adapter.DiscoveryAdapter;
import com.example.mycloudmusic.domain.BaseMultiItemEntity;
import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.domain.Title;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.domain.response.ListResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.network.Api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 首页-“发现”界面
 */
public class DiscoveryFragment extends BaseCommonFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    private GridLayoutManager layoutManager;
    private DiscoveryAdapter adapter;

    @Override
    protected void initViews() {
        super.initViews();

        //高度固定
        rv.setHasFixedSize(true);

        //设置显示3列
        layoutManager = new GridLayoutManager(getMainActivity(), 3);
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //创建适配器
        adapter = new DiscoveryAdapter();

        //设置列宽度
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                //在这里
                //获取模型上面的宽度
                return adapter.getItem(position).getSpanSize();
            }
        });

        //添加头部
        adapter.addHeaderView(createHeaderView());

        //设置适配器
        rv.setAdapter(adapter);

        //请求数据
        fetchData();
    }


    /**
     * 创建头部布局
     *
     * @return
     */
    private View createHeaderView() {
        //从XML创建View
        View view = getLayoutInflater().inflate(R.layout.header_discovery, (ViewGroup) rv.getParent(), false);

        //找到日期文本控件
        TextView tv_day = view.findViewById(R.id.tv_day);

        //获取日历对象
        Calendar calendar = Calendar.getInstance();

        //获取当前月的天
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_day.setText(String.valueOf(day));
        //返回控件
        return view;
    }

    /**
     * 请求数据
     */
    private void fetchData() {
//        //因为现在还没有请求数据
//        //所以添加一些测试数据
//        //目的是让列表显示出来
//        List<BaseMultiItemEntity> datum = new ArrayList<>();
//
//        //添加标题
//        datum.add(new Title("推荐歌单"));
//
//        //添加歌单数据
//        for (int i = 0; i < 9; i++) {
//            datum.add(new Sheet());
//        }
//
//        //添加标题
//        datum.add(new Title("推荐单曲"));
//
//        //添加单曲数据
//        for (int i = 0; i < 9; i++) {
//            datum.add(new Song());
//        }
//
//        //将数据设置（替换）到适配器
//        adapter.replaceData(datum);

        //创建列表
        List<BaseMultiItemEntity> datum = new ArrayList<>();
        //添加标题
        datum.add(new Title("推荐歌单"));
        //歌单API
        Observable<ListResponse<Sheet>> sheets = Api.getInstance().sheets();
        //单曲API
        Observable<ListResponse<Song>> songs = Api.getInstance().songs();
        //请求歌单数据
        sheets.subscribe(new HttpObserver<ListResponse<Sheet>>() {
            @Override
            public void onSucceeded(ListResponse<Sheet> data) {
                //添加歌单数据
                datum.addAll(data.getData());

                //请求单曲数据
                songs.subscribe(new HttpObserver<ListResponse<Song>>() {
                    @Override
                    public void onSucceeded(ListResponse<Song> data) {
                        //添加标题
                        datum.add(new Title("推荐单曲"));
                        //添加单曲数据
                        datum.addAll(data.getData());
                        //设置数据到适配器
                        adapter.replaceData(datum);
                    }
                });
            }
        });
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
