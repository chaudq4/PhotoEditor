package com.chauduong.photoeditor.Manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class PreviewManager {
    private Context mContext;
    private ImageView imgPreview;

    public PreviewManager(Context mContext, ImageView imgPreview) {
        this.mContext = mContext;
        this.imgPreview = imgPreview;
    }

    public void setImage(Bitmap bitmap) {
        if (imgPreview != null && bitmap != null) {
            imgPreview.post(() -> imgPreview.setImageBitmap(bitmap));
        }
    }
}
