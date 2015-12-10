package com.zczczy.leo.shengjingspecialcar.activities;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;
import com.zczczy.leo.shengjingspecialcar.prefs.MyPrefs_;
import com.zczczy.leo.shengjingspecialcar.rest.MyErrorHandler;
import com.zczczy.leo.shengjingspecialcar.rest.MyRestClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.springframework.util.StringUtils;

/**
 * Created by leo on 2015/10/18.
 */

@EActivity
public abstract class BaseActivity extends AppCompatActivity{


    @SystemService
    InputMethodManager inputMethodManager;

    @SystemService
    ConnectivityManager connectivityManager;

    @Pref
    MyPrefs_ myPrefs;

    @RestService
    MyRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;


    /**
     * 获取当前网络类型
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */

    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;



    @AfterInject
    void afterBaseInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(80)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.08f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
//                .setScrimColor(Color.BLUE)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(200)//activity联动时的偏移量。默认500px。
                .addListener(new SwipeListener() {//滑动监听
                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                    }

                    @Override
                    public void onEdgeTouch() {//当开始滑动
                    }

                    @Override
                    public void onScrollToClose() {//当滑动关闭
                    }
                });
    }

    /**
     * 检测网络是否可用
     * @return
     */
    public boolean isNetworkConnected() {
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    /**
     * 获取当前网络类型
     * @return 0：没有网络   1：WIFI网络   2：WAP网络    3：NET网络
     */
    public int getNetworkType() {
        int netType = 0;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if(!StringUtils.isEmpty(extraInfo)){
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }




    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    @Override
    public void finish() {
        if (inputMethodManager.isActive()){
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        super.finish();
    }
}
