package com.chauduong.photoeditor.Model;

public class ToneItem {
    private String name;
    private int value;

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

