package com.zczczy.leo.shengjingspecialcar;

import android.app.Application;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

/**
 * Created by leo on 2015/10/18.
 */

@EApplication
public class MyApplication extends Application {

    @AfterInject
    void afterInject(){

//        CrashWoodpecker.fly().to(this);
    }
}
