package com.example.mycloudmusic.activity;

import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

import com.example.mycloudmusic.R;

import butterknife.BindView;

/**
 * 通用标题界面
 */
public class BaseTitleActivity extends BaseCommonActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void initViews() {
        super.initViews();
        //初始化toolbar，就是将其设置到系统
        setSupportActionBar(toolbar);

        //是否显示返回按钮
        if (isShowBackMenu()) {
            showBackMenu();
        }
    }

    /**
     * 显示返回按钮
     */
    protected void showBackMenu() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    /**
     * 是否显示返回按钮
     */
    protected boolean isShowBackMenu() {
        return true;
    }

    /**
     * 按钮点击回调事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //固定的id
            case android.R.id.home:
                //Toolbar返回按钮点击
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
