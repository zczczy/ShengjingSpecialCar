package com.zczczy.leo.shengjingspecialcar.activities;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.zczczy.leo.shengjingspecialcar.R;
import com.zczczy.leo.shengjingspecialcar.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CustomTitle;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Leo on 2015/12/10.
 */
@EActivity(R.layout.main_layout)
public class MainActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    MapView mapView;

    AMap aMap;

    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
    }


    @AfterViews
    void afterView() {
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        mapView.onCreate(savedInstanceState);// 必须要写
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
