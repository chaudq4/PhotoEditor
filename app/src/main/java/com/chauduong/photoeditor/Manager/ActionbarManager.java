package com.chauduong.photoeditor.Manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauduong.photoeditor.Interface.ActionbarListener;
import com.chauduong.photoeditor.R;

import butterknife.Action;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActionbarManager implements View.OnClickListener {
    Context mContext;
    TextView txtSave;
    TextView txtReset;
    ImageView imgOpen;
    ImageView imgMore;
    ActionbarListener mActionbarListener;

    public ActionbarManager(Context mContext) {
        this.mContext = mContext;
        txtSave=((Activity) mContext).findViewById(R.id.txtSave);
        txtReset=((Activity) mContext).findViewById(R.id.txtReset);
        imgOpen=((Activity) mContext).findViewById(R.id.imgOpen);
        imgMore=((Activity) mContext).findViewById(R.id.imgMore);
        txtSave.setOnClickListener(this);
        txtReset.setOnClickListener(this);
        imgOpen.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        enabaleReset(false);
        enableSave(false);

    }

    public void setmActionbarListener(ActionbarListener mActionbarListener) {
        this.mActionbarListener = mActionbarListener;
    }

    public void enableSave(boolean isEnable){
        txtSave.setClickable(isEnable);
        txtSave.setTextColor(isEnable?mContext.getColor(R.color.white):mContext.getColor(R.color.dim));
    }
    public void enabaleReset(boolean isEnable){
        txtReset.setClickable(isEnable);
        txtReset.setTextColor(isEnable?mContext.getColor(R.color.white):mContext.getColor(R.color.dim));
    }

    @Override
    public void onClick(View v) {
        mActionbarListener.onActionbarClick(v);
    }

}
