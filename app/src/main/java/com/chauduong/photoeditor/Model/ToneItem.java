package com.chauduong.photoeditor.Model;

public class ToneItem {
    private String name;
    private float value;
    private int drawable;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ToneItem(int state, String name, float value, int drawable) {
        this.state= state;
        this.name = name;
        this.value = value;
        this.drawable = drawable;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public ToneItem(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public ToneItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ToneItem{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", drawable=" + drawable +
                ", state=" + state +
                '}';
    }
}

