package com.zczczy.leo.shengjingspecialcar.activities;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.zczczy.leo.shengjingspecialcar.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by Leo on 2015/12/10.
 */
@EActivity(R.layout.main_layout)
public class MainActivity extends BaseActivity {


    @AfterViews
    void afterView() {

        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);

    }


}
