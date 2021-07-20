package com.chauduong.photoeditor.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chauduong.photoeditor.Model.ToneItem;

import static com.chauduong.photoeditor.Fragment.ToneFragment.EXPOSURE_STATE;
import static com.chauduong.photoeditor.Fragment.ToneFragment.CONTRAST_STATE;
import static com.chauduong.photoeditor.Fragment.ToneFragment.SATURATION_STATE;

public class Utils {
    public static void setProgressBar(SeekBar sb, ToneItem toneItem) {
        int value = 0;
        switch (toneItem.getState()) {
            case EXPOSURE_STATE:
                value = (int) toneItem.getValue();
                break;
            case CONTRAST_STATE:
                value = (int) (toneItem.getValue() * 10);
                break;
            case SATURATION_STATE:
                value = (int) (toneItem.getValue() * 10);
                break;
        }
        Log.i("chau", "setProgressBar: " + value + " " + toneItem.getState());
        sb.setProgress(value);
    }

    public static void setTextValueTone(TextView textView, float value, int currentState) {
        switch (currentState) {
            case EXPOSURE_STATE:
                value = (int) value;
                break;
            case CONTRAST_STATE:
                value = value * 10;
                break;
            case SATURATION_STATE:
                value = value * 10;
                break;
        }
        textView.setText(String.valueOf(value));
    }

    public static String findCall() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[3];//maybe this number needs to be corrected
        String methodName = e.getMethodName();
        return methodName;
    }

    public static boolean isEqual(float a, float b) {
        int result = (int) (a - b);
        return result == 0;
    }

    public static void setFullScreen(Context mContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) mContext).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity) mContext).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
