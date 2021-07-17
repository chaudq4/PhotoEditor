package com.chauduong.photoeditor.Utils;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chauduong.photoeditor.Model.ToneItem;

import static com.chauduong.photoeditor.Fragment.ToneFragment.BRIGHTNESS_STATE;
import static com.chauduong.photoeditor.Fragment.ToneFragment.CONTRAST_STATE;
import static com.chauduong.photoeditor.Fragment.ToneFragment.SATURATION_STATE;

public class Utils {
    public static void setProgressBar(SeekBar sb, ToneItem toneItem) {
        int value = 0;
        switch (toneItem.getState()) {
            case BRIGHTNESS_STATE:
                value = (int) toneItem.getValue() ;
                break;
            case CONTRAST_STATE:
                value = (int) (toneItem.getValue()  *10);
                break;
            case SATURATION_STATE:
                value = (int) (toneItem.getValue()*10);
                break;
        }
        Log.i("chau", "setProgressBar: "+value+" "+toneItem.getState());
        sb.setProgress(value);
    }

    public static void setTextValueTone(TextView textView, float value, int currentState) {
        switch (currentState){
            case BRIGHTNESS_STATE:
                value = (int) value;
                break;
            case CONTRAST_STATE:
                value = value  *10;
                break;
            case SATURATION_STATE:
                value =  value*10;
                break;
        }
        textView.setText(String.valueOf(value));
    }
    public static String  findCall(){
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
        String methodName = e.getMethodName();
        return methodName;
    }
    public static boolean isEqual(float a, float b){
        int result= (int) (a-b);
        return result==0;
    }

}
