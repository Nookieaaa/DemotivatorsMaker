package com.example.nookie.demotivatorsmaker.models;


import android.net.Uri;

public class RVItem {
    private Uri file;
    private Uri thumbnail;
    private boolean checked;

    public RVItem(Uri file, Uri thumbnail) {
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

    public Uri getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Uri thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
