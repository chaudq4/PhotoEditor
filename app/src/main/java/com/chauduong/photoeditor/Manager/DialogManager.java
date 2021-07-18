package com.chauduong.photoeditor.Manager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chauduong.photoeditor.Interface.DialogManagerListener;
import com.chauduong.photoeditor.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogManager {
    public static final int SAVE_DIALOG = 100;
    public static final int SAVE_ERROR = 200;
    public static final int SAVE_COMPLETED = 300;
    public static final int PERMISSION_DENY = 400;
    public static final int NO_IMAGE = 500;
    ProgressDialog mProgressDialog;
    Context mContext;
    DialogManagerListener mDialogManagerListener;
    AlertDialog mCurrentDialog;
    @BindView(R.id.btnOK)
    Button btnOK;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    public DialogManagerListener getmDialogManagerListener() {
        return mDialogManagerListener;
    }

    public void setmDialogManagerListener(DialogManagerListener mDialogManagerListener) {
        this.mDialogManagerListener = mDialogManagerListener;
    }

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

    public void dissmissDialog() {
        if (mCurrentDialog != null && mCurrentDialog.isShowing())
            mCurrentDialog.dismiss();
    }

    public void showDialog(int type) {
        switch (type) {
            case SAVE_DIALOG:
                showSaveDialog();
                break;
        }
        if (mCurrentDialog != null && !mCurrentDialog.isShowing())
            mCurrentDialog.show();
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mCurrentDialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mCurrentDialog.getWindow().setAttributes(layoutParams);
//        Button btnOk= mCurrentDialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        LinearLayout.LayoutParams lpOK = (LinearLayout.LayoutParams)btnOk.getLayoutParams();
//        lpOK.gravity = Gravity.CENTER_HORIZONTAL;
//        btnOk.setLayoutParams(lpOK);
//        Button btnCancel= mCurrentDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
//        LinearLayout.LayoutParams lpCancel = (LinearLayout.LayoutParams)btnCancel.getLayoutParams();
//        lpCancel.gravity = Gravity.CENTER;
//        btnCancel.setLayoutParams(lpCancel);

    }

    private void showSaveDialog() {
        View saveDialogLayout = LayoutInflater.from(mContext).inflate(R.layout.save_dialog, null);
        ButterKnife.bind(this, saveDialogLayout);
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setView(saveDialogLayout).create();

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManagerListener.onClickOKSave();

            }
        });
        btnCancel = saveDialogLayout.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogManagerListener.onClickCancelSave();
            }
        });
        mCurrentDialog = alertDialog;


    }

    public void showToast(int type) {
        Toast toast = null;
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
