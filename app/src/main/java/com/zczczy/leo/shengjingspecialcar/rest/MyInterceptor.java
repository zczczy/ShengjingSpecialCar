package com.zczczy.leo.shengjingspecialcar.rest;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * Created by leo on 2015/7/21.
 */

@EBean(scope = EBean.Scope.Singleton)
public class MyInterceptor implements ClientHttpRequestInterceptor {

    @RootContext
    Context context;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] data,
                                        ClientHttpRequestExecution execution) throws IOException {
        // do something
        //afterCheck();
        HttpHeaders headers = request.getHeaders();

        return execution.execute(request, data);
    }

}
