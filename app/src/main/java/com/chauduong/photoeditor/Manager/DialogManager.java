package com.chauduong.photoeditor.Manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chauduong.photoeditor.R;

public class DialogManager {
    public static final int SAVE_DIALOG = 100;
    public static final int SAVE_ERROR = 200;
    public static final int SAVE_COMPLETED = 300;
    public static final int PERMISSION_DENY = 400;
    public static final int NO_IMAGE = 500;
    ProgressDialog mProgressDialog;
    Context mContext;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }


    public void showProgressDialog() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle(mContext.getString(R.string.save_image_dialog));
        Window window = mProgressDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;

        window.setAttributes(wlp);
        mProgressDialog.show();
    }

    public void dissmissProgessDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    public void showToast(int type) {
        Toast toast=null;
        switch (type) {
            case SAVE_ERROR:
                toast = Toast.makeText(mContext, mContext.getString(R.string.save_error), Toast.LENGTH_SHORT);
                break;
            case SAVE_COMPLETED:
                toast = Toast.makeText(mContext, mContext.getString(R.string.save_completed), Toast.LENGTH_SHORT);
                break;
            case PERMISSION_DENY:
                toast = Toast.makeText(mContext, mContext.getString(R.string.permission_deny), Toast.LENGTH_SHORT);
                break;
            case NO_IMAGE:
                toast = Toast.makeText(mContext, mContext.getString(R.string.no_image), Toast.LENGTH_SHORT);
                break;
        }
        if (toast != null)
            toast.show();

    }
}
