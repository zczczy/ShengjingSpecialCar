package com.zczczy.leo.shengjingspecialcar.items;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by leo on 2015/8/29.
 */
public class ViewWrapper <V extends View> extends RecyclerView.ViewHolder  {

    private Context context;

    private V view;


    public ViewWrapper(V itemView,ViewGroup parent) {
        super(itemView);
        context= parent.getContext();
        view = itemView;
        //view.setOnClickListener(this);

    }

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }


    public V getView() {
        return view;
    }

//    @Override
//    public void onItemSelected() {
//
//    }
//
//    @Override
//    public void onItemClear() {
//
//    }
}
