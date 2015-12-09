package com.zczczy.leo.shengjingspecialcar.rest;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.Trace;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.core.NestedRuntimeException;

/**
 * Created by leo on 2015/7/21.
 */

@EBean
public class MyErrorHandler implements RestErrorHandler {

    @RootContext
    Context context;

//    @StringRes
//    String no_net;

    @Trace
    @Override
    public void onRestClientExceptionThrown(NestedRuntimeException arg0) {
        // TODO Auto-generated method stub
        //LogUtils.e("=================", arg0.getMessage());
        //开启 线程运行 否者报错
        //showTos();
    }


}
