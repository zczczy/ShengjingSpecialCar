package com.zczczy.leo.shengjingspecialcar.model;

/**
 * Created by leo on 2015/7/21.
 */
/**
 * 无返回数据的操作结果类
 */
public class BaseModel {

    public BaseModel(){
    }

    public BaseModel(boolean Successful,String Error){
        this.Successful=Successful;
        this.Error=Error;
    }

    public boolean Successful;

    public String Error;


}
