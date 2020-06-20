package com.example.mycloudmusic.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.adapter.SongAdapter;
import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.ImageUtil;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.ResourceUtil;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import org.apache.commons.lang3.StringUtils;

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
    private LinearLayout ll_header;
    private ImageView iv_banner;
    private TextView tv_title;
    private ImageView iv_avatar;
    private TextView tv_nickname;
    private LinearLayout ll_comment_container;
    private TextView tv_comment_count;
    private Button bt_collection;
    private LinearLayout ll_play_all_container;
    private TextView tv_count;


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

//        //分割线
//        DividerItemDecoration decoration = new DividerItemDecoration(getMainActivity(), RecyclerView.VERTICAL);
//
//        //添加到控件
//        //可以添加多个
//        rv.addItemDecoration(decoration);
    }

    /**
     * 创建头部
     *
     * @return
     */
    private View createHeaderView() {
        //从XML创建View
        View view = getLayoutInflater().inflate(R.layout.header_sheet_detail, (ViewGroup) rv.getParent(), false);

        //头部容器
        ll_header = view.findViewById(R.id.ll_header);

        //封面图
        iv_banner = view.findViewById(R.id.iv_banner);

        //标题
        tv_title = view.findViewById(R.id.tv_title);

        //歌单创建者头像
        iv_avatar = view.findViewById(R.id.iv_avatar);

        //歌单创建者昵称
        tv_nickname = view.findViewById(R.id.tv_nickname);

        //评论容器
        ll_comment_container = view.findViewById(R.id.ll_comment_container);

        //评论数
        tv_comment_count = view.findViewById(R.id.tv_comment_count);

        //收藏按钮
        bt_collection = view.findViewById(R.id.bt_collection);

        //播放全部容器
        ll_play_all_container = view.findViewById(R.id.ll_play_all_container);

        //歌曲数
        tv_count = view.findViewById(R.id.tv_count);

        //返回View
        return view;
    }

    @Override
    protected void initDatum() {
        super.initDatum();
//        //获取传递的参数
//        id = getIntent().getStringExtra(Constant.ID);
        id = extraId();

        //创建适配器
        adapter = new SongAdapter(R.layout.item_song_detail);

        //添加头部
        adapter.addHeaderView(createHeaderView());

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

//        //显示封面
//        if (StringUtils.isBlank(data.getBanner())) {
//            //如果图片为空
//
//            //就用默认图片
//            iv_banner.setImageResource(R.drawable.dnf);
//        } else {
//            //有图片
//
//            ImageUtil.show(getMainActivity(), iv_banner, data.getBanner());
//        }


//        //使用Palette获取封面颜色
//        if (StringUtils.isBlank(data.getBanner())) {
//            //图片为空
//
//            //使用默认图片
//            iv_banner.setImageResource(R.drawable.dnf);
//        } else {
//            //有图片
//
//            //这是一个典型的构建者模式
//            //由于这是项目课程
//            //所以这里不详细讲解设计模式
//            Glide.with(this)
//                    .asBitmap()
//                    .load(ResourceUtil.resourceUri(data.getBanner()))
//
//                    //加载图片到自定义目标
//                    //为什么是自定义目标
//                    //是因为我们要获取Bitmap
//                    //然后获取Bitmap的一些颜色
//                    .into(new CustomTarget<Bitmap>() {
//
//                        /**
//                         * 资源加载完成调用
//                         *
//                         * @param resource
//                         * @param transition
//                         */
//                        @Override
//                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                            //显示封面
//                            iv_banner.setImageBitmap(resource);
//
//                            //在Material Design(MD，材料设计，是Google的一门设计语言)的设计中
//                            //所谓的设计语言就是一些设计规范
//                            //目前Google已经应用到Android，Gmail等产品
//
//                            //推荐我们将应用的状态栏
//                            //标题栏的颜色和当前页面的内容融合
//                            //也就说当前页面显示一张红色的图片
//                            //那么最好状态栏，标题栏的颜色也和红色差不多
//                            //实现这种效果可以借助的Palette类。
//                            //Palette:可以翻译为调色板
//                            //功能是可以从图片中获取一些颜色
//                            //详细的可以学习《详解Material Design，http://www.ixuea.com/courses/9》课程
//                            Palette.from(resource)
//                                    .generate(new Palette.PaletteAsyncListener() {
//
//                                        /**
//                                         * 颜色计算完成了
//                                         * @param palette
//                                         */
//                                        @Override
//                                        public void onGenerated(@Nullable Palette palette) {
//                                            //获取 有活力 的颜色
//                                            Palette.Swatch swatch = palette.getVibrantSwatch();
//
//                                            //可能没有值所以要判断
//                                            if (swatch != null) {
//                                                //获取颜色的rgb
//                                                int rgb = swatch.getRgb();
//
//                                                //设置标题颜色
//                                                toolbar.setBackgroundColor(rgb);
//
//                                                //设置头部容器颜色
//                                                ll_header.setBackgroundColor(rgb);
//
//                                                //这些api只有高版本才有
//                                                //所以说要判断
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                                    //设置状态栏颜色
//                                                    Window window = getWindow();
//
//                                                    window.setStatusBarColor(rgb);
//
//                                                    //设置导航栏颜色
//                                                    window.setNavigationBarColor(rgb);
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//
//                        /**
//                         * 加载任务取消了
//                         * 可以在这里释放我们定义的一些资源
//                         *
//                         * @param placeholder
//                         */
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                        }
//                    });
//        }

        //使用GlidePalette获取封面颜色
        if (StringUtils.isBlank(data.getBanner())) {
            //图片为空

            //使用默认图片
            iv_banner.setImageResource(R.drawable.dnf);
        } else {
            //有图片

            //获取图片绝对路径
            String uri = ResourceUtil.resourceUri(data.getBanner());

            //加载
            GlidePalette<Drawable> glidePalette = GlidePalette

                    //再把地址传到GlidePalette
                    .with(uri)

                    //使用VIBRANT颜色样板
                    .use(GlidePalette.Profile.VIBRANT)

                    //设置到控件背景
                    .intoBackground(toolbar, GlidePalette.Swatch.RGB)
                    .intoBackground(ll_header, GlidePalette.Swatch.RGB)

                    //设置回调
                    //用回调的目的是
                    //要设置状态栏和导航栏
                    .intoCallBack(new BitmapPalette.CallBack() {
                        @Override
                        public void onPaletteLoaded(@Nullable Palette palette) {
                            //获取 有活力 的颜色
                            Palette.Swatch swatch = palette.getVibrantSwatch();

                            //可能没有值所以要判断
                            if (swatch != null) {
                                //获取颜色的rgb
                                int rgb = swatch.getRgb();

                                //这些api只有高版本才有
                                //所以说要判断
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    //设置状态栏颜色
                                    Window window = getWindow();

                                    window.setStatusBarColor(rgb);

                                    //设置导航栏颜色
                                    window.setNavigationBarColor(rgb);
                                }
                            }
                        }
                    })

                    //淡入
                    //只有第一次效果很明显
                    //由于这里是项目课程
                    //所以就不在深入查看是为什么了
                    //如果大家感兴趣可以深入查看
                    //搞懂了也可以在群里分享给大家
                    .crossfade(true);


            //使用Glide
            Glide.with(getMainActivity())

                    //加载图片
                    .load(uri)

                    //加载完成监听器
                    .listener(glidePalette)

                    //将图片设置到图片控件
                    .into(iv_banner);
        }

        //显示标题
        tv_title.setText(data.getTitle());

        //头像
        ImageUtil.showAvatar(getMainActivity(), iv_avatar, data.getUser().getAvatar());

        //昵称
        tv_nickname.setText(data.getUser().getNickname());

        //评论数
        tv_comment_count.setText(String.valueOf(data.getComments_count()));

        //音乐数
        tv_count.setText(getResources().getString(R.string.music_count, data.getSongs_count()));
    }
}

