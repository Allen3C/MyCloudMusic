package com.example.mycloudmusic.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mycloudmusic.AppContext;
import com.example.mycloudmusic.MainActivity;
import com.example.mycloudmusic.R;
import com.example.mycloudmusic.domain.Session;
import com.example.mycloudmusic.domain.User;
import com.example.mycloudmusic.domain.response.DetailResponse;
import com.example.mycloudmusic.listener.HttpObserver;
import com.example.mycloudmusic.network.Api;
import com.example.mycloudmusic.util.LogUtil;
import com.example.mycloudmusic.util.StringUtil;
import com.example.mycloudmusic.util.ToastUtil;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.OnClick;

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
//                        //显示加载提示框
//                        LoadingUtil.showLoading(getMainActivity());
//                    }
//
//                    @Override
//                    public void onNext(SheetDetailWrapper sheetDetailWrapper) {
//                        LogUtil.d(TAG, "request sheet detail success:" + sheetDetailWrapper.getData().getTitle());
//                        //隐藏加载提示框
//                        LoadingUtil.hideLoading();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.d(TAG, "request sheet detail failed:" + e.getLocalizedMessage());
//                        //隐藏加载提示框
//                        LoadingUtil.hideLoading();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        //测试加载提示框
//        LoadingUtil.showLoading(getMainActivity());
//        //3秒后自动隐藏
//        //因为显示后无法点击后面的按钮
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LoadingUtil.hideLoading();
//            }
//        }, 3000);

//        //请求歌单列表数据
//        //请求歌单列表
//        Api.getInstance().sheets()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<SheetListWrapper>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(SheetListWrapper sheetWrapper) {
//                        LogUtil.d(TAG,"onNext:"+sheetWrapper.getData().size());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

//        //使用DetailResponse
//        Api.getInstance().sheetDetail("1").subscribe(new Observer<DetailResponse<Sheet>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(DetailResponse<Sheet> sheetDetailResponse) {
//                LogUtil.d(TAG, "request sheet detail success:" + sheetDetailResponse.getData().getTitle());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        //使用ListResponse
//        Api.getInstance().sheets().subscribe(new Observer<ListResponse<Sheet>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(ListResponse<Sheet> sheetListResponse) {
//                LogUtil.d(TAG,"onNext:"+sheetListResponse.getData().size());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        //使用ObserverAdapter
//        Api.getInstance().sheetDetail("1").subscribe(new ObserverAdapter<DetailResponse<Sheet>>(){
//            @Override
//            public void onNext(DetailResponse<Sheet> sheetDetailResponse) {
//                super.onNext(sheetDetailResponse);
//                LogUtil.d(TAG, "request sheet detail success:" + sheetDetailResponse.getData().getTitle());
//            }
//        });

//        //使用HttpObserver
//        Api.getInstance().sheetDetail("1").subscribe(new HttpObserver<DetailResponse<Sheet>>() {
//            @Override
//            public void onSucceeded(DetailResponse<Sheet> data) {
//                LogUtil.d(TAG, "request sheet detail success:" + data.getData().getTitle());
//            }
//        });

//        //模拟500错误
//        Api.getInstance().userDetail("-1", "11111").subscribe(new HttpObserver<DetailResponse<User>>() {
//            @Override
//            public void onSucceeded(DetailResponse<User> data) {
//                LogUtil.d(TAG, "onNext:" + data.getData());
//            }
//        });

//

//        //测试自动显示隐藏对话框
//        Api.getInstance().sheetDetail("1").subscribe(new HttpObserver<DetailResponse<Sheet>>(getMainActivity(), true) {
//            @Override
//            public void onSucceeded(DetailResponse<Sheet> data) {
//                LogUtil.d(TAG, "onNext:" + data.getData().getTitle());
//            }
//        });

        //获取用户名
        String username = et_username.getText().toString().trim();
        if(StringUtils.isBlank(username)){
            LogUtil.w(TAG, "onLoginClick username empty");
            ToastUtil.errorShortToast(R.string.enter_username);
            return;
        }
        //如果用户名
        //不是手机号也不是邮箱
        //就是格式错误
        if(!(StringUtil.isPhone(username) || StringUtil.isEmail(username))){
            ToastUtil.errorShortToast( R.string.error_username_format);
            return;
        }
        //获取密码
        String password = et_password.getText().toString().trim();
        if(StringUtils.isBlank(password)){
            LogUtil.w(TAG, "onLoginClick password empty");
            ToastUtil.errorShortToast( R.string.enter_password);
            return;
        }
        //判断密码格式
        if(!(StringUtil.isPassword(password))){
            ToastUtil.errorShortToast(R.string.error_password_format);
            return;
        }

        //登录
        //判断手机号还是邮箱
        String phone = null;
        String email = null;
        if(StringUtil.isPhone(username)){
            phone = username;
        }else {
            email = username;
        }

        User user = new User();
        //这里虽然同时传递了手机号和邮箱
        //但服务端登录的时候有先后顺序
        user.setPhone(phone);
        user.setEmail(email);
        user.setPassword(password);
        //调用登录接口
        Api.getInstance()
                .login(user)
                .subscribe(new HttpObserver<DetailResponse<Session>>() {
                    @Override
                    public void onSucceeded(DetailResponse<Session> data) {
                        LogUtil.d(TAG,"onLoginClick success:"+data.getData().getSession());

                        //把登录成功的事件通知到AppContext
                        AppContext.getInstance().login(sp, data.getData());

                        ToastUtil.successShortToast(R.string.login_success);

                        //关闭当前界面并启动主界面
                        startActivityAfterFinnishThis(MainActivity.class);
                    }
                });
    }
    /**
     * 忘记密码按钮点击了
     */
    @OnClick(R.id.bt_forget_password)
    public void onForgetPasswordClick() {
        LogUtil.d(TAG, "onForgetPasswordClick");
    }
}
