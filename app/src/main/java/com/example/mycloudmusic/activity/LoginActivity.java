package com.example.mycloudmusic.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.SheetDetailWrapper;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.network.Service;
import com.example.mycloudmusic.util.Constant;
import com.example.mycloudmusic.util.LoadingUtil;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.StringUtil;
import com.example.mycloudmusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 登录界面
 */
public class LoginActivity extends BaseTitleActivity {

    private static final String TAG = "LoginActivity";
    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_password)
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * 登录按钮点击
     * @param button
     */
    @OnClick(R.id.bt_login)
    public void onLoginClick(Button button){
        LogUtil.d(TAG, "onLoginClick");

//        //用来测试网络请求框架
//        //初始化OkHttp
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//
//        //初始化Retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                //让Retrofit使用OkHttp请求网络
//                .client(builder.build())
//
//                //这里就是配置API地址
//                .baseUrl(Constant.ENDPOINT)
//
//                //适配RxJava
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//
//                //自动JSON转换
//                //包括请求参数和相应
//                .addConverterFactory(GsonConverterFactory.create())
//                //创建Retrofit
//                .build();
//
//        //创建一个service
//        Service service = retrofit.create(Service.class);
//        //请求歌单详情
//        service.sheetDetail("1")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SheetDetailWrapper>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(SheetDetailWrapper sheetDetailWrapper) {
//                        //请求成功
//                        LogUtil.d(TAG,"request sheet detail success:"+sheetDetailWrapper.getData().getTitle());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        //请求失败
//                        LogUtil.d(TAG,"request sheet detail failed:"+e.getLocalizedMessage());
//
//                        //判断错误类型
//                        if (e instanceof UnknownHostException) {
//                            ToastUtil.errorShortToast(R.string.error_network_unknown_host);
//                        }else if (e instanceof ConnectException) {
//                            ToastUtil.errorShortToast(R.string.error_network_connect);
//                        }else if(e instanceof SocketTimeoutException){
//                            ToastUtil.errorShortToast(R.string.error_network_timeout);
//                        }else if (e instanceof HttpException){
//                            HttpException exception = (HttpException) e;
//                            //获取响应码
//                            int code = exception.code();
//                            if (code == 401) {
//                                ToastUtil.errorShortToast(R.string.error_network_not_auth);
//                            } else if (code == 403) {
//                                ToastUtil.errorShortToast(R.string.error_network_not_permission);
//                            } else if (code == 404) {
//                                ToastUtil.errorShortToast(R.string.error_network_not_found);
//                            } else if (code >= 500) {
//                                ToastUtil.errorShortToast(R.string.error_network_server);
//                            } else {
//                                ToastUtil.errorShortToast(R.string.error_network_unknown);
//                            }
//                        } else{
//                            ToastUtil.errorShortToast(R.string.error_network_unknown);
//                        }
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        //使用重构后的API
//        Api.getInstance()
//                .sheetDetail("1")
//                .subscribe(new Observer<SheetDetailWrapper>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(SheetDetailWrapper sheetDetailWrapper) {
//                        LogUtil.d(TAG, "request sheet detail success:" + sheetDetailWrapper.getData().getTitle());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.d(TAG, "request sheet detail failed:" + e.getLocalizedMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

            //测试加载提示框
        LoadingUtil.showLoading(getMainActivity());
        //3秒后自动隐藏
//因为显示后无法点击后面的按钮
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadingUtil.hideLoading();
            }
        }, 3000);
//        //获取用户名
//        String username = et_username.getText().toString().trim();
//        if(StringUtils.isBlank(username)){
//            LogUtil.w(TAG, "onLoginClick username empty");
////            Toast.makeText(getMainActivity(), R.string.enter_username, Toast.LENGTH_SHORT).show();
//            ToastUtil.errorShortToast(R.string.enter_username);
//            return;
//        }
//        //如果用户名
//        //不是手机号也不是邮箱
//        //就是格式错误
//        if(!(StringUtil.isPhone(username) || StringUtil.isEmail(username))){
//            ToastUtil.errorShortToast( R.string.error_username_format);
//            return;
//        }
//        //获取密码
//        String password = et_password.getText().toString().trim();
//        if(StringUtils.isBlank(password)){
//            LogUtil.w(TAG, "onLoginClick password empty");
////            Toast.makeText(getMainActivity(), R.string.enter_password, Toast.LENGTH_SHORT).show();
//            ToastUtil.errorShortToast( R.string.enter_password);
//            return;
//        }
//        //判断密码格式
//        if(!(StringUtil.isPassword(password))){
//            ToastUtil.errorShortToast(R.string.error_password_format);
//            return;
//        }
//        // TODO: 20-5-18 调用登录方法
//        ToastUtil.successShortToast(R.string.login_success);
    }
    /**
     * 忘记密码按钮点击了
     */
    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick() {
        LogUtil.d(TAG, "onForgetPasswordClick");
    }
}
