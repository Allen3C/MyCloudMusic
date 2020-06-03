package com.example.mycloudmusic;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mycloudmusic.activity.BaseCommonActivity;
import com.example.mycloudmusic.activity.BaseTitleActivity;
import com.example.mycloudmusic.activity.SettingActivity;
import com.example.mycloudmusic.activity.WebViewActivity;
import com.example.mycloudmusic.adapter.MainAdpter;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.ImageUtil;
import com.example.mycloudmusic.util.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseTitleActivity {

    /**
     * 侧滑控件
     */
    @BindView(R.id.dl)
    DrawerLayout dl;

    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindView(R.id.vp)
    ViewPager vp;

    @BindView(R.id.mi)
    MagicIndicator mi;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //处理动作
        processIntent(getIntent());
    }

    @Override
    protected void initViews() {
        super.initViews();

        //侧滑配置
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //侧滑监听器
        dl.addDrawerListener(toggle);

        //同步状态
        toggle.syncState();

        //缓存页面数量
        //默认是缓存一个
        vp.setOffscreenPageLimit(4);

        //创建Adapter
        MainAdpter adapter = new MainAdpter(getMainActivity(), getSupportFragmentManager());
        vp.setAdapter(adapter);

        //创建占位数据
        ArrayList<Integer> datum = new ArrayList<>();
        datum.add(0);
        datum.add(1);
        datum.add(2);
        datum.add(3);
        adapter.setDatum(datum);

        //将指示器和ViewPager关联起来
//创建通用的指示器
        CommonNavigator commonNavigator = new CommonNavigator(getMainActivity());

/**
 * 设置适配器
 */
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            /**
             * 指示器数量
             *
             * @return
             */
            @Override
            public int getCount() {
                return datum.size();
            }

            /**
             * 获取当前位置的标题
             *
             * @param context
             * @param index
             * @return
             */
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                //创建简单的文本控件
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);

                //默认颜色
                titleView.setNormalColor(getResources().getColor(R.color.tab_normal));

                //选中后的颜色
                titleView.setSelectedColor(getResources().getColor(R.color.white));

                //显示的文本
                titleView.setText(adapter.getPageTitle(index));

                //点击回调监听
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击相关的指示器
                        //让ViewPager跳转到当前位置
                        vp.setCurrentItem(index);
                    }
                });
                return titleView;
            }

            /**
             * 返回指示器
             *
             * 就是下面那条线
             *
             * @param context
             * @return
             */
            @Override
            public IPagerIndicator getIndicator(Context context) {
                //创建一条线
                //LinePagerIndicator indicator = new LinePagerIndicator(context);

                //线的宽度和内容一样
                //indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);

                //高亮颜色
                //indicator.setColors(Color.WHITE);
                //return indicator;

                //返回null表示不显示指示器
                return null;
            }
        });

        //如果位置显示不下指示器时
        //是否自动调整
        commonNavigator.setAdjustMode(true);

        //设置导航器
        mi.setNavigator(commonNavigator);

        //将ViewPager和指示器关联
        ViewPagerHelper.bind(mi, vp);
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        //默认选中第二个界面
        vp.setCurrentItem(1);

        //获取用户信息
        //当然可以在用户要显示侧滑的时候
        //才获取用户信息
        //这样可以减少请求
        fetchData();
    }

    /**
     * 请求数据
     */
    private void fetchData() {
        Api.getInstance().userDetail(sp.getUserId())
                .subscribe(new HttpObserver<DetailResponse<User>>() {
                    @Override
                    public void onSucceeded(DetailResponse<User> data) {
                        next(data.getData());
                    }
                });
    }

    /**
     * 显示数据
     * @param data
     */
    private void next(User data) {

//        //显示资源目录图片
//        //就是应用中drawable和mipmap目录
//        Glide.with(this).load(R.drawable.dnf).into(iv_avatar);

//        //测试网络地址图片
//        Glide
//                .with(this)
//                .load("http://dev-courses-misuc.ixuea.com/1da1c001e89c4b8780ac8f9780ef881f.jpg")
//                .into(iv_avatar);

//        //其他配置
//        Glide
//                .with(this)
//                .load(R.drawable.dnf)
//
//                //从中心裁剪
//                .centerCrop()
//
//                //占位图
//                //就是当前真实的图片没有显示出来前
//                //显示的图片
//                .placeholder(R.drawable.dnf)
//                .into(iv_avatar);

        //显示头像
        ImageUtil.showAvatar(getMainActivity(), iv_avatar, data.getAvatar());

        //显示昵称
        tv_nickname.setText(data.getNickname());

        //显示描述
        tv_description.setText(data.getDescriptionFormat());
    }

    /**
     * 处理动作
     * @param intent
     */
    private void processIntent(Intent intent) {

        if(Constant.ACTION_AD.equals(intent.getAction())){
            //广告点击

            //显示广告界面
            WebViewActivity.start(getMainActivity(),"活动详情",intent.getStringExtra(Constant.URL));
        }
    }

    /**
     * 界面已经显示了
     * 不需要再次创建新界面的时候调用
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtil.d(TAG, "onNewIntent");

        //处理动作
        processIntent(intent);
    }

    /**
     * 用户容器点击
     */
    @OnClick(R.id.ll_user)
    public void onUserClick() {
        LogUtil.d(TAG,"onUserClick");
    }

    /**
     * 设置点击
     */
    @OnClick(R.id.ll_setting)
    public void onSettingClick() {
        LogUtil.d(TAG,"onSettingClick");
        startActivity(SettingActivity.class);

        //关闭侧滑
        closeDrawer();
    }

    /**
     * 关闭抽屉
     */
    private void closeDrawer() {
        dl.closeDrawer(GravityCompat.START);
    }
}
