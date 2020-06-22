package com.example.mycloudmusic.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.adapter.SongAdapter;
import com.example.mycloudmusic.domain.Sheet;
import com.example.mycloudmusic.domain.Song;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.manager.ListManager;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.service.MusicPlayerService;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.ImageUtil;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.ResourceUtil;
import com.example.mycloudmusic.util.ToastUtil;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;

/**
 * 歌单详情界面
 */
public class SheetDetailActivity extends BaseTitleActivity implements View.OnClickListener {

    private static final String TAG = "SheetDetailActivity";

    @BindView(R.id.rv)
    RecyclerView rv;

    /**
     * 迷你播放控制器 容器
     */
    @BindView(R.id.ll_play_control_small)
    LinearLayout ll_play_control_small;

    /**
     * 迷你播放控制器 封面
     */
    @BindView(R.id.iv_banner_small_control)
    ImageView iv_banner_small_control;

    /**
     * 迷你播放控制器 标题
     */
    @BindView(R.id.tv_title_small_control)
    TextView tv_title_small_control;

    /**
     * 迷你播放控制器 播放暂停按钮
     */
    @BindView(R.id.iv_play_small_control)
    ImageView iv_play_small_control;

    /**
     * 迷你播放控制器 进度条
     */
    @BindView(R.id.pb_progress_small_control)
    ProgressBar pb_progress_small_control;

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
    private LinearLayout ll_user;
    private ListManager listManager;


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

        //用户容器
        ll_user = view.findViewById(R.id.ll_user);

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

        //初始化列表管理器
        listManager = MusicPlayerService.getListManager(getApplicationContext());

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

    @Override
    protected void initListeners() {
        super.initListeners();
        //用户点击事件
        ll_user.setOnClickListener(this);

        //收藏按钮单击事件
        bt_collection.setOnClickListener(this);

        //评论点击事件
        ll_comment_container.setOnClickListener(this);

        //设置item点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //SimplePlayerActivity.start(getMainActivity());
                play(position);
            }
        });
    }

    /**
     * 播放当前位置音乐
     * @param position
     */
    private void play(int position) {
        //获取当前位置播放的音乐
        Song data = adapter.getItem(position);

        //把当前歌单所有音乐设置到播放列表
        //有些应用
        //可能会实现添加到已经播放列表功能
        listManager.setDatum(adapter.getData());

        //播放当前音乐
        listManager.play(data);

        //简单播放器界面
        SimplePlayerActivity.start(getMainActivity());
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

        //显示收藏状态
        showCollectionStatus();
    }

    /**
     * 显示收藏状态
     */
    @SuppressLint("ResourceType")
    private void showCollectionStatus() {
        if (data.isCollection()) {
            //收藏了

            //将按钮文字改为取消
            bt_collection.setText(getResources().getString(R.string.cancel_collection_all,data.getCollections_count()));

            //弱化取消收藏按钮
            //因为我们的本质是想让用户收藏歌单
            //所以去掉背景
            bt_collection.setBackground(null);

            //设置文字颜色为灰色
            bt_collection.setTextColor(getResources().getColor(R.color.light_grey));
        } else {
            //没有收藏

            //将按钮文字改为收藏
            bt_collection.setText(getResources().getString(R.string.collection_all,data.getCollections_count()));

            //设置按钮颜色为主色调
            bt_collection.setBackgroundResource(R.drawable.selector_color_primary);

            //将文字颜色设置为白色
            bt_collection.setTextColor(getResources().getColorStateList(R.drawable.selector_text_color_primary_reverse));
        }
    }

    /**
     * 按钮点击回调方法
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_collection:
                //收藏歌单按钮点击了
                processCollectionClick();
                break;
            case R.id.ll_comment_container:
//                //评论容器点击了
//                Intent intent = new Intent(getMainActivity(), CommentActivity.class);
//                //添加歌单ID
//                intent.putExtra(Constant.SHEET_ID, data.getId());
//                //启动界面
//                startActivity(intent);

                //使用重构的方法
                CommentActivity.start(getMainActivity(),data.getId());
                break;
            case R.id.ll_user:
                //用户容器点击
                startActivityExtraId(UserDetailActivity.class, data.getUser().getId());
                break;
        }
    }

    /**
     * 处理收藏和取消收藏逻辑
     */
    private void processCollectionClick() {
        LogUtil.d(TAG, "processCollectionClick");
        if (data.isCollection()) {
            //已经收藏了

            //取消收藏
            Api.getInstance()
                    .deleteCollect(data.getId())
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> d) {
                            //弹出提示
                            ToastUtil.successShortToast(R.string.cancel_success);

                            //重新加载数据
                            //目的是显示新的收藏状态
                            //fetchData();

                            //取消收藏成功
                            //SheetDetailActivity.this.data.setCollection_id(null);
                            data.setCollection_id(null);

                            //收藏数-1
                            data.setCollections_count(data.getCollections_count() - 1);

                            //刷新状态
                            showCollectionStatus();
                        }
                    });
        } else {
            //没有收藏

            //收藏
            Api.getInstance()
                    .collect(data.getId())
                    .subscribe(new HttpObserver<Response<Void>>() {
                        @Override
                        public void onSucceeded(Response<Void> d) {
                            //弹出提示
                            ToastUtil.successShortToast(R.string.collection_success);

                            //重新加载数据
                            //目的是显示新的收藏状态
                            //fetchData();

                            //收藏状态变更后
                            //可以重新调用歌单详情界面接口
                            //获取收藏状态
                            //但对于收藏来说
                            //收藏数可能没那么重要
                            //所以不用及时刷新
                            data.setCollection_id(1);

                            //收藏数+1
                            data.setCollections_count(data.getCollections_count() + 1);

                            //刷新状态
                            showCollectionStatus();
                        }
                    });
        }
    }

    /**
     * 创建菜单方法
     * 有点类似显示布局要在onCreate方法中
     * 使用setContentView设置布局
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载按钮布局
        getMenuInflater().inflate(R.menu.menu_sheet_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单点击了回调
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //获取点击的菜单id
        int id = item.getItemId();

        if (R.id.action_search == id) {
            //搜索按钮点击了
            LogUtil.d(TAG, "search menu click");

            return true;
        } else if (R.id.action_sort == id) {
            //排序按钮点击了
            LogUtil.d(TAG, "sort menu click");

            return true;
        } else if (R.id.action_report == id) {
            //举报按钮点击了
            LogUtil.d(TAG, "report menu click");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 迷你播放控制器 容器点击
     */
    @OnClick(R.id.ll_play_control_small)
    public void onPlayControlSmallClick() {
        LogUtil.d(TAG, "onPlayControlSmallClick");

    }

    /**
     * 迷你播放控制器 播放暂停按钮点击
     */
    @OnClick(R.id.iv_play_small_control)
    public void onPlaySmallClick() {
        LogUtil.d(TAG, "onPlaySmallClick");
    }

    /**
     * 迷你播放控制器 下一曲按钮点击
     */
    @OnClick(R.id.iv_next_small_control)
    public void onNextSmallClick() {
        LogUtil.d(TAG, "onNextSmallClick");
    }

    /**
     * 迷你播放控制器 播放列表按钮点击
     */
    @OnClick(R.id.iv_list_small_control)
    public void onListSmallClick() {
        LogUtil.d(TAG, "onListSmallClick");
    }
}

