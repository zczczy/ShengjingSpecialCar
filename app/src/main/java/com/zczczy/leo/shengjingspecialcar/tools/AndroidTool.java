package com.zczczy.leo.shengjingspecialcar.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.zczczy.leo.shengjingspecialcar.R;
import com.zczczy.leo.shengjingspecialcar.viewgroup.LoadingDialog;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leo on 2015/7/21.
 */
public class AndroidTool {

    private static ProgressDialog infoDialog;
    private static String MPHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
    private static String TPHONE = "^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$";

    /**
     * 显示等待对话框
     *
     * @param context
     */
    public static void showLoadDialog(Context context) {
        if (infoDialog == null) {
            infoDialog = new LoadingDialog(context, "正在加载..", R.anim.loading);
            infoDialog.setCancelable(false);
            infoDialog.show();
        } else if (!infoDialog.isShowing()&& infoDialog.getContext() == context) {
            infoDialog.setCancelable(false);
            infoDialog.show();
        } else if (!infoDialog.isShowing()&&infoDialog.getContext() != context) {
            infoDialog = new LoadingDialog(context, "正在加载..", R.anim.loading);
            infoDialog.setCancelable(false);
            infoDialog.show();
        }
    }

    /**
     * 判断是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 隐藏等待对话框
     */
    public static void dismissLoadDialog() {
        if (infoDialog != null && infoDialog.isShowing()) {
            infoDialog.dismiss();
        }
    }

    /**
     * 显示 Toast
     *
     * @param context
     * @param msg
     *            消息
     */
    public static void showToast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示 Toast
     *
     * @param context
     * @param msg
     *            消息
     */
    public static void showToast(Fragment context, String msg) {

        Toast.makeText(context.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 显示 Toast
     *
     * @param context
     * @param msg
     *            消息
     */
    public static void showToast(android.support.v4.app.Fragment context, String msg) {

        Toast.makeText(context.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param context
     * @param title
     *            标题
     * @param items
     *            元素
     * @param checkId
     *            当前元素的id
     * @param listener
     *            监听器
     */
    public static void showSinglenChoice(Context context, String title,
                                         String[] items, int checkId,
                                         DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(items, checkId > 0 ? checkId : 0,
                        listener).create().show();
    }

    public static Dialog showCustomDialogNoTitle(Context context, View view) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        return dialog;
    }




    /**
     *
     * @param title
     *            标题
     * @param view
     *            编辑内容
     * @param listener
     *            监听器
     * @return
     */
    public static Dialog showEditDialog(String title, EditText view,
                                        DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton("确定", listener);
        return builder.create();
    }

    /**
     * 判断是否为空
     *
     * @param context
     * @param txts
     * @return
     */
    public static boolean checkNotNull(Context context, String... txts) {
        for (String t : txts) {
            if (t == null || t.length() == 0) {
                if (context != null)
                    showToast(context, "请正确填写！！！");
                return false;
            }
        }
        return true;
    }

    /***
     * 去掉数组中重复的 元素
     *
     * @param resource
     * @return String[]
     */
    public static String[] Array_unique(String[] resource) {
        // array_unique
        List<String> list = new LinkedList<String>();
        for (int i = 0; i < resource.length; i++) {
            if (!list.contains(resource[i])) {
                list.add(resource[i]);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * 判断EditText是否为空！ 如果为空 返回true 否者返回false
     *
     * @param e
     * @return
     */
    public static boolean checkIsNull(EditText e) {
        if (e == null) {
            return true;
        }
        return "".equals(e.getText().toString().trim());
    }

    /**
     * 判断TextView是否为空！ 如果为空 返回true 否者返回false
     *
     * @param e
     * @return
     */
    public static boolean checkTextViewIsNull(TextView e) {
        if (e == null) {
            return true;
        }
        return "".equals(e.getText().toString().trim());
    }

    /**
     * 判断手机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param mPhone
     * @return
     */
    public static boolean checkMPhone(EditText mPhone) {
        Pattern p = Pattern.compile(MPHONE);
        Matcher m = p.matcher(mPhone.getText().toString().trim());

        return !m.matches();
    }
    /**
     * 判断手机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param mPhone
     * @return
     */
    public static boolean checkMPhone(TextView mPhone) {
        Pattern p = Pattern.compile(MPHONE);
        Matcher m = p.matcher(mPhone.getText().toString().trim());

        return !m.matches();
    }

    /**
     * 判断座机号码 是否有误！ 如果有误 返回true 否者返回false
     *
     * @param tPhone
     * @return
     */
    public static boolean checkTPhone(EditText tPhone) {
        Pattern p = Pattern.compile(TPHONE);
        Matcher m = p.matcher(tPhone.getText().toString().trim());
        return !m.matches();
    }

    public static boolean isNetConnected(Context context) {
        boolean isNetConnected;
        // 获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            // String name = info.getTypeName();
            // L.i("当前网络名称：" + name);
            isNetConnected = true;
        } else {
            isNetConnected = false;
        }
        return isNetConnected;
    }

    /**
     * 安装.apk文件
     *
     * @param context
     */
    public void install(Context context, String fileName)
    {
        if (TextUtils.isEmpty(fileName) || context == null)
        {
            return;
        }
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 安装.apk文件
     *
     * @param context
     */
    public void install(Context context, File file)
    {
        try
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
