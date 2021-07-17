package com.chauduong.photoeditor.Manager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.chauduong.photoeditor.Utils.BitmapUtils;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Random;

import static com.chauduong.photoeditor.MainActivity.SELECT_GALLERY_IMAGE;

public class ImageManager {
    private DialogManager mDialogManager;
    private Context mContext;
    private Handler mHandler;
    private Uri filePath;

    public ImageManager(DialogManager mDialogManager, Context mContext) {
        this.mDialogManager = mDialogManager;
        this.mContext = mContext;
        mHandler = new Handler(Looper.getMainLooper());
    }


    public Uri getFilePath() {
        return filePath;
    }

    public void setFilePath(Uri filePath) {
        this.filePath = filePath;
    }

    public void saveImageToGallery() {
        Dexter.withActivity((Activity) mContext).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            if (filePath == null) mDialogManager.showToast(DialogManager.NO_IMAGE);
                            mDialogManager.showProgressDialog();
                            save();
                        } else {
                            mDialogManager.showToast(DialogManager.PERMISSION_DENY);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();


    }

    private void save() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapUtils.getBitmapFromGallery(mContext, filePath, 1);
                bitmap = Editor.getFilter().processFilter(bitmap.copy(Bitmap.Config.ARGB_8888,true));
                Filter myFilter = new Filter();
                myFilter.addSubFilter(new BrightnessSubFilter(Editor.getBrightnessFinal()));
                myFilter.addSubFilter(new ContrastSubFilter(Editor.getContrastFinal()));
                myFilter.addSubFilter(new SaturationSubfilter(Editor.getSaturationFinal()));
                final Bitmap finalBitmap = myFilter.processFilter(bitmap.copy(Bitmap.Config.ARGB_8888, true));
                String root = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).toString();
                File myDir = new File(root + "/saved_images");
                myDir.mkdirs();
                Random generator = new Random();

                int n = 10000;
                n = generator.nextInt(n);
                String fname = "Image-" + n + ".jpg";
                File file = new File(myDir, fname);
                if (file.exists()) file.delete();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDialogManager.dissmissProgessDialog();
                            mDialogManager.showToast(DialogManager.SAVE_COMPLETED);
                        }
                    });


                } catch (Exception e) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDialogManager.dissmissProgessDialog();
                            mDialogManager.showToast(DialogManager.SAVE_ERROR);
                        }
                    });

                }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
                notifyGalleryNewImage(file);
            }
        }).start();
    }

    public void openImageFromGallery() {
        Dexter.withActivity((Activity) mContext).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            ((Activity) mContext).startActivityForResult(intent, SELECT_GALLERY_IMAGE);
                        } else {
                            mDialogManager.showToast(DialogManager.PERMISSION_DENY);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    public void notifyGalleryNewImage(File file) {
        MediaScannerConnection.scanFile(mContext, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }

}
