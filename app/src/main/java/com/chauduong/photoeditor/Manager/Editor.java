package com.chauduong.photoeditor.Manager;

import com.zomato.photofilters.imageprocessors.Filter;

public class Editor {
    static Filter filter;
    static int brightnessFinal;
    static float saturationFinal;
    static float contrastFinal;

    public Editor() {
    }

    public static Filter getFilter() {
        return filter;
    }

    public static void setFilter(Filter filter) {
        Editor.filter = filter;
    }

    public static int getBrightnessFinal() {
        return brightnessFinal;
    }

    public static void setBrightnessFinal(int brightnessFinal) {
        Editor.brightnessFinal = brightnessFinal;
    }

    public static float getSaturationFinal() {
        return saturationFinal;
    }

    public static void setSaturationFinal(float saturationFinal) {
        Editor.saturationFinal = saturationFinal;
    }

    public static float getContrastFinal() {
        return contrastFinal;
    }

    public static void setContrastFinal(float contrastFinal) {
        Editor.contrastFinal = contrastFinal;
    }
    public static void resetAll(){
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }
}
