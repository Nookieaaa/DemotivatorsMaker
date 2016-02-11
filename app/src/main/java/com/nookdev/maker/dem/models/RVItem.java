package com.nookdev.maker.dem.models;


import android.graphics.Bitmap;
import android.net.Uri;

import lombok.Getter;
import lombok.Setter;

public class RVItem {
    private @Getter @Setter Uri file;
    private @Getter @Setter Bitmap thumbnail;
    private @Getter @Setter boolean checked;


    public RVItem(Uri file, Bitmap thumbnail) {
        this.file = file;
        this.thumbnail = thumbnail;
    }

    public RVItem(Uri file) {
        this.file = file;
    }

}
