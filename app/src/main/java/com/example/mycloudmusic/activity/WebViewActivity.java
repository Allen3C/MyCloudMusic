package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.Constant;

import butterknife.BindView;

/**
 * 通用WebView界面
 */
public class WebViewActivity extends BaseTitleActivity {


    /**
     * WebView控件
     */
    @BindView(R.id.wv)
    WebView wv;

    /**
     * 定义静态的启动方法
     * 好处是用户只要看到声明
     * 就知道该界面需要哪些参数
     * @param activity
     * @param title
     * @param url
     */
    public static void start(Activity activity, String title, String url) {
        //创建Intent
        Intent intent = new Intent(activity, WebViewActivity.class);

        //添加标题
        intent.putExtra(Constant.TITLE, title);

        //添加url
        intent.putExtra(Constant.URL, url);

        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
    }

    @Override
    protected void initViews() {
        super.initViews();

        //获取webview设置
        WebSettings webSettings = wv.getSettings();

        //允许访问文件
        webSettings.setAllowFileAccess(true);

        //支持多窗口
        webSettings.setSupportMultipleWindows(true);

        //开启js支持
        webSettings.setJavaScriptEnabled(true);

        //显示图片
        webSettings.setBlockNetworkImage(false);

        //允许显示手机中的网页
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        //允许文件访问
        webSettings.setAllowFileAccessFromFileURLs(true);

        //允许dom存储
        webSettings.setDomStorageEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            //允许混合类型
            //也就是说支持http和https混合的网页
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        //获取传递的数据
        String title = extraString(Constant.TITLE);
        String url = extraString(Constant.URL);

        //设置表头
        setTitle(title);

        //加载网址
        wv.loadUrl(url);
    }
}
