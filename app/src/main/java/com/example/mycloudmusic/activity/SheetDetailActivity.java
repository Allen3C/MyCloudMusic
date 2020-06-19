package com.example.mycloudmusic.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.Constant;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity {

    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_detail);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //获取传递的参数
        id = getIntent().getStringExtra(Constant.ID);
    }
}

