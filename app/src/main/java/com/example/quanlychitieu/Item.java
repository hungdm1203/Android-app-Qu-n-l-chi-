package com.example.quanlychitieu;

public class Item {
    private String type;
    private int img;

    public Item(String type, int img) {
        this.type = type;
        this.img = img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
