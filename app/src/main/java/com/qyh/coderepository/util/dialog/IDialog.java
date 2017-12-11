package com.qyh.coderepository.util.dialog;

import android.content.Context;
import android.content.DialogInterface;

/**
 * @author 邱永恒
 * @time 2017/11/16  16:09
 * @desc ${TODD}
 */

public interface IDialog {

    void init(Context context);
    // 显示加载框
    void showLoadingDialog(String title, String tip, OnDialogCancelListener listener);
    // 修改加载框描述
    void changeLoadingDialog(String title, String tip);
    // 隐藏加载框
    void hideLoadingDialog();

    // 显示进度框
    void showProgressDialog(String title, int max, DialogInterface.OnClickListener listener);

    // 显示对话框
    void showAlertDialog(String title, String msg, String cancel, String ok, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener okListener);
    // 显示自定义对话框
    void showAlertDialog();
    // 隐藏对话框
    void hideAlertDialog();

}
