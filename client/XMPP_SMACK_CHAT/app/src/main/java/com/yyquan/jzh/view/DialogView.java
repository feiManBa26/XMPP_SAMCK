package com.yyquan.jzh.view;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DialogView {

    public static SweetAlertDialog pDialog;

    public static void Initial(Context context, String message) {
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(message);
       pDialog.setCancelable(true);
       pDialog.setCanceledOnTouchOutside(true);
    }

    public static void show() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    public static void dismiss() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

}
