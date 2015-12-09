package com.zczczy.leo.shengjingspecialcar.rest;

import android.content.Context;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.springframework.http.client.OkHttpClientHttpRequestFactory;

/**
 * Created by leo on 2015/7/21.
 */
@EBean
public class MyRequestFactory extends OkHttpClientHttpRequestFactory {

    @RootContext
    Context context;

    @AfterInject
    void afterInject(){
        this.setConnectTimeout(30 * 1000);
        this.setReadTimeout(30 * 1000);

    }
}
