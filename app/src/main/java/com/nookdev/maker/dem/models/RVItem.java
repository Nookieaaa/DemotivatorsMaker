package com.nookdev.maker.dem.models;


import android.graphics.Bitmap;
import android.net.Uri;

public class RVItem {
    private Uri file;
    private Bitmap thumbnail;
    private boolean checked;

    public RVItem(Uri file, Bitmap thumbnail) {
        this.file = file;
        this.thumbnail = thumbnail;
    }

    public RVItem(Uri file) {
        this.file = file;
    }

    public Uri getFile() {
        return file;
    }

    public void setFile(Uri file) {
        this.file = file;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
