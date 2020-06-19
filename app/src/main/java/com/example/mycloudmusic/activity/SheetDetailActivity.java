package com.example.mycloudmusic.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.adapter.SongAdapter;
import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.LogUtil;

import butterknife.BindView;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity {

    private static final String TAG = "SheetDetailActivity";

    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 歌单id
     */
    private String id;
    /**
     * 歌单
     */
    private Sheet data;
    private SongAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_detail);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //尺寸固定
        rv.setHasFixedSize(true);

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        rv.setLayoutManager(layoutManager);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
//        //获取传递的参数
//        id = getIntent().getStringExtra(Constant.ID);
        id = extraId();

        //创建适配器
        adapter = new SongAdapter(R.layout.item_song_detail);

        //设置适配器
        rv.setAdapter(adapter);

        fetchData();
    }

    private void fetchData() {
        Api.getInstance()
                .sheetDetail(id)
                .subscribe(new HttpObserver<DetailResponse<Sheet>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Sheet> data) {
                        next(data.getData());
                    }
                });
    }

    /**
     * 显示数据
     *
     * @param data
     */
    private void next(Sheet data) {
        this.data = data;
        LogUtil.d(TAG, "next :" + data);
        if (data.getSongs() != null && data.getSongs().size() > 0) {
            //有音乐才设置

            //设置数据
            adapter.replaceData(data.getSongs());
        }
    }
}

