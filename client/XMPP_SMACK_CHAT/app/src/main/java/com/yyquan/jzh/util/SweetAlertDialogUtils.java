package com.yyquan.jzh.util;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * File: SweetAlertDialogUtils.java
 * Author: ejiang
 * Version: V100R001C01
 * Create: 2018-01-16 16:28
 */

public class SweetAlertDialogUtils {
    private String massage;
    private SweetAlertDialogListener mListener;
    private boolean isTouch;
    private Context mContext;
    private SweetAlertDialog mDialog;
    private int type;

    private SweetAlertDialogUtils(Builder builder) {
        this.massage = builder.massage;
        this.mListener = builder.mListener;
        this.mContext = builder.mContext;
        this.type = builder.type;
        init();
    }

    private void init() {
        mDialog = new SweetAlertDialog(mContext, type);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setCanceledOnTouchOutside(isTouch);
        mDialog.setCancelable(isTouch);
        mDialog.setContentText(massage);
        mDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog dialog) {
                if(mListener!=null){
                    mListener.clickCancel();
                }
            }
        });
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog dialog) {
                if(mListener!=null){
                    mListener.ClickOk();
                }
            }
        });
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        mDialog.show();
    }

    public static SweetAlertDialogUtils.Builder builder() {
        return new SweetAlertDialogUtils.Builder();
    }

    public static class Builder {
        private Context mContext;
        private String massage;
        private SweetAlertDialogListener mListener;
        private boolean isTouch;
        private int type;

        private Builder() {
            this.type = SweetAlertDialog.PROGRESS_TYPE;
        }

        public SweetAlertDialogUtils.Builder setType(int type) {
            this.type = type;
            return this;
        }

        public SweetAlertDialogUtils.Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public SweetAlertDialogUtils.Builder setTouch(boolean touch) {
            this.isTouch = touch;
            return this;
        }

        public SweetAlertDialogUtils.Builder setMassage(String massage) {
            this.massage = massage;
            return this;
        }

        public SweetAlertDialogUtils.Builder setListener(SweetAlertDialogListener listener) {
            mListener = listener;
            return this;
        }

        public SweetAlertDialogUtils build() {
            return new SweetAlertDialogUtils(this);
        }
    }

    public void cancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
