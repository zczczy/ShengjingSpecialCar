package com.zczczy.leo.shengjingspecialcar.broadcast;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.support.content.AbstractBroadcastReceiver;

/**
 * Created by leo on 2015/7/21.
 */

@EReceiver
public class MyBroadcast extends AbstractBroadcastReceiver {

    @SystemService
    ConnectivityManager manager;


    @ReceiverAction(value=ConnectivityManager.CONNECTIVITY_ACTION,dataSchemes = "http")
    public void connectivity(){
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if(activeInfo==null){
            BackgroundExecutor.cancelAll("",true);
        }
    }

}
