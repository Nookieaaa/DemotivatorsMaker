package com.example.nookie.demotivatorsmaker.models;


import android.graphics.Bitmap;

public class Demotivator {
    private Bitmap image;
    private String caption;
    private String text;

    public Demotivator(Bitmap image, String caption, String text) {
        this.image = image;
        this.caption = caption;
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
