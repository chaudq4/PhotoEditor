package com.chauduong.photoeditor.Manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
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
    @BindView(R.id.imgReset)
    FrameLayout txtReset;
    @BindView(R.id.imgOpen)
    FrameLayout imgOpen;
    @BindView(R.id.imgMore)
    FrameLayout imgMore;
    @BindView(R.id.imgSave)
    FrameLayout imgSave;
    ActionbarListener mActionbarListener;

    public ActionbarManager(Context mContext) {
        this.mContext = mContext;
        ButterKnife.bind(this, (Activity) mContext);
        imgSave.setOnClickListener(this);
        txtReset.setOnClickListener(this);
        imgOpen.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        enabaleReset(false);
        enableSave(false);

    }

    public void setmActionbarListener(ActionbarListener mActionbarListener) {
        this.mActionbarListener = mActionbarListener;
    }

    public void enableSave(boolean isEnable) {
        imgSave.setClickable(isEnable);
//        imgSave.setTextColor(isEnable ? mContext.getColor(R.color.white) : mContext.getColor(R.color.dim));
    }

    public void enabaleReset(boolean isEnable) {
        txtReset.setClickable(isEnable);
    }

    @Override
    public void onClick(View v) {
        mActionbarListener.onActionbarClick(v);
    }

}
