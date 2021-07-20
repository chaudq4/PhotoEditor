package com.chauduong.photoeditor.Manager;

import android.util.Log;

import com.chauduong.photoeditor.Utils.Utils;
import com.zomato.photofilters.imageprocessors.Filter;

public class EditorManager {
    static Filter filter;
    static int exposureFinal = 0;
    static float saturationFinal = 1;
    static float contrastFinal = 1;
    static int degress =0;
    static boolean isFilpX =false;

    public static boolean isIsFilpX() {
        return isFilpX;
    }

    public static void setIsFilpX(boolean isFilpX) {
        EditorManager.isFilpX = isFilpX;
    }



    public static int getDegress() {
        return degress;
    }

    public static void setDegress(int degress) {
        EditorManager.degress = degress;
    }

    public static Filter getFilter() {
        return filter;
    }

    public static void setFilter(Filter filter) {
        EditorManager.filter = filter;
    }

    public static int getExposureFinal() {
        return exposureFinal;
    }

    public static void setExposureFinal(int exposureFinal) {
        EditorManager.exposureFinal = exposureFinal;
    }

    public static float getSaturationFinal() {
        return saturationFinal;
    }

    public static void setSaturationFinal(float saturationFinal) {
        EditorManager.saturationFinal = saturationFinal;
    }

    public static float getContrastFinal() {
        return contrastFinal;
    }

    public static void setContrastFinal(float contrastFinal) {
        EditorManager.contrastFinal = contrastFinal;
    }

    public static void resetAll() {
        filter = new Filter();
        filter.setName(null);
        exposureFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
        degress =0;
        isFilpX=false;
    }

    public static void showAllEdit() {
        Log.i("chau", "showAllEdit: " + " " + exposureFinal + " " + contrastFinal + " " + saturationFinal);
    }

    public static boolean isHasChange() {
        if (!Utils.isEqual(degress,0)||(filter != null && filter.getName() != null) ||isIsFilpX()
                || !Utils.isEqual(saturationFinal * 10, 10) || !Utils.isEqual(10, contrastFinal * 10) || !Utils.isEqual(exposureFinal, 0)) {
            return true;
        }
        return false;
    }

}
