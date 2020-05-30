package com.example.mycloudmusic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import com.example.mycloudmusic.MainActivity;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.util.LogUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告界面（启动界面后的广告）
 */
public class AdActivity extends BaseCommonActivity {

    private static final String TAG = "AdActivity";
    @BindView(R.id.bt_skip_ad)
    Button bt_skip_ad;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
    }

    @Override
    protected void initViews() {
        super.initViews();
        //全屏
        fullScreen();
    }

    /**
     * 界面以及显示出来了调用
     * 从后台切换到前台还会调用
     */
    @Override
    protected void onResume() {
        super.onResume();

        //倒计时的总时间,间隔
        //单位为毫秒
        countDownTimer = new CountDownTimer(5000, 1000) {

            /**
             * 每次间隔调用
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                //每次回调
                bt_skip_ad.setText(getString(R.string.count_second, millisUntilFinished / 1000));
            }

            /**
             * 倒计时完成
             */
            @Override
            public void onFinish() {
                //倒计时完成
                //执行下一步
                next();
            }
        };

        //启动定时器
        countDownTimer.start();
    }

    /**
     * 进入首页
     */
    private void next() {
        //startActivityAfterFinnishThis(MainActivity.class);
        //显示网页
        WebViewActivity.start(getMainActivity(),"活动详情","http://www.ixuea.com");
    }

    /**
     * 广告点击了
     */
    @OnClick(R.id.bt_ad)
    public void onAdClick() {
        LogUtil.d(TAG, "onAdClick");
    }

    /**
     * 跳过广告按钮
     */
    @OnClick(R.id.bt_skip_ad)
    public void onSkipAd() {
        LogUtil.d(TAG, "onSkipAd");

        //取消倒计时
        cancelCountDown();

        //执行下一步
        next();
    }

    /**
     * 取消倒计时
     */
    private void cancelCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
