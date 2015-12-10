package com.zczczy.leo.shengjingspecialcar.rest;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;


import com.zczczy.leo.shengjingspecialcar.MyApplication;
import com.zczczy.leo.shengjingspecialcar.listener.OttoBus;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2015/8/23.
 */
@EBean
public class MyBackgroundTask {

    @RootContext
    Context context;

    @Bean
    OttoBus bus;

    @StringRes
    String netTimeout,noNet;

    @SystemService
    ConnectivityManager connManager;

    @RestService
    MyRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

//    @App
//    MyApplication myApplication;


    Cursor cursor;

    String[] projection = new String[]{
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE};

    String orderBy = MediaStore.Video.Media.DISPLAY_NAME;

    Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    ContentResolver contentResolver;


//    @Pref
//    MyPrefs_ myPrefs_;

    @AfterInject
    void afterInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
        //bus.register(this);
    }

    @AfterViews
    void afterView(){

    }

    /**
     * 判断网络是否连接
     *
     * @return
     */

    private boolean isConnected(){

        if (null != connManager)
        {

            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断是否是wifi连接
     */
    public  boolean isWifi()
    {
        if (connManager == null)
            return false;
        return connManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
       //bus.unregister(this);
    }
}
