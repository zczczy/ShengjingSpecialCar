package com.zczczy.leo.shengjingspecialcar.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;


import com.zczczy.leo.shengjingspecialcar.prefs.MyPrefs_;
import com.zczczy.leo.shengjingspecialcar.rest.MyErrorHandler;
import com.zczczy.leo.shengjingspecialcar.rest.MyRestClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by leo on 2015/7/21.
 */
@EFragment
public abstract class BaseFragment extends Fragment {


    @Pref
    MyPrefs_ myPrefs;

    @RestService
    MyRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;


    @AfterInject
    void afterBaseInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
    }


    @Override
    public void onPause() {
        super.onPause();
        if (isVisible()){
            onHiddenChanged(isVisible());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()){
            onHiddenChanged(isHidden());
        }
    }

    //不好使
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
            Log.e("setUserVisibleHint","相当于Fragment的onPause");
        } else {
            //相当于Fragment的onPause
            Log.e("setUserVisibleHint","相当于Fragment的onPause");
        }
    }


}
