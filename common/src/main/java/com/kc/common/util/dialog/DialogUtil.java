package com.kc.common.util.dialog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * @author 邱永恒
 * @time 2017/11/16  14:24
 * @desc ${TODD}
 */

public class DialogUtil implements IDialog {

    private Context context;
    private ProgressDialog loadingDialog;
    private AlertDialog alertDialog;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void showLoadingDialog(String title, String tip, final OnDialogCancelListener listener) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(context);
        }
        loadingDialog.setTitle(title);
        loadingDialog.setMessage(tip);
        // 设置dialog外面不可点击
        loadingDialog.setCanceledOnTouchOutside(false);
        // 设置返回键不可取消显示
        loadingDialog.setCancelable(false);

        loadingDialog.show();
        loadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                listener.onCancel();
            }
        });
    }

    @Override
    public void changeLoadingDialog(String title, String tip) {
        if (loadingDialog != null) {
            loadingDialog.setTitle(title);
            loadingDialog.setMessage(tip);
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.hide();
            }
        }
    }

    @Override
    public void showProgressDialog(String title, int max, DialogInterface.OnClickListener listener) {

    }

    @Override
    public void showAlertDialog(String title, String msg, String cancel, String ok, DialogInterface.OnClickListener cancelListener, DialogInterface.OnClickListener okListener) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context).create();
        }
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, cancel, cancelListener);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, ok, okListener);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void showAlertDialog() {

    }

    @Override
    public void hideAlertDialog() {
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }
}
