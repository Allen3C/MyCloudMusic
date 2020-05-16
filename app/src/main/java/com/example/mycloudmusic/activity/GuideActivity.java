package com.example.mycloudmusic.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.mycloudmusic.MainActivity;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.adapter.GuideAdapter;
import com.example.mycloudmusic.fragment.GuideFragment;
import com.example.mycloudmusic.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * 引导界面，只有下载之后第一次打开显示
 */
public class GuideActivity extends BaseCommonActivity implements View.OnClickListener {
    private Button bt_login_or_register;
    private Button bt_enter;
    private static final String TAG = "GuideActivity";
    private ViewPager vp;
    private GuideAdapter adapter;
    private CircleIndicator ci;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //隐藏状态栏
        hideStatusBar();

        vp = findViewById(R.id.vp);

        ci = findViewById(R.id.ci);

        bt_login_or_register = findViewById(R.id.bt_login_or_register);
        bt_enter = findViewById(R.id.bt_enter);
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //创建适配器
        adapter = new GuideAdapter(getMainActivity(), getSupportFragmentManager());
        //设置适配器到ViewPager
        vp.setAdapter(adapter);
        //让指示器根据列表控件配合工作
        ci.setViewPager(vp);
        //适配器注册数据源观察者
        adapter.registerDataSetObserver(ci.getDataSetObserver());
        //准备数据
        List<Integer> datum = new ArrayList<>();
        datum.add(R.drawable.guide1);
        datum.add(R.drawable.guide2);
        datum.add(R.drawable.guide3);
        datum.add(R.drawable.guide4);
        datum.add(R.drawable.guide5);
        //设置数据到适配器
        adapter.setDatum(datum);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        bt_login_or_register.setOnClickListener(this);
        bt_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login_or_register:
                Log.e(TAG, "register");
                startActivityAfterFinnishThis(LoginOrRegisterActivity.class);
                setShowGuide();
                break;
            case R.id.bt_enter:
                Log.e(TAG, "enter");
                startActivityAfterFinnishThis(MainActivity.class);
                setShowGuide();
                break;
        }
    }

    /**
     * 设置 不再 显示引导界面
     */
    private void setShowGuide() {
        sp.setShowGuide(false);
    }
}
