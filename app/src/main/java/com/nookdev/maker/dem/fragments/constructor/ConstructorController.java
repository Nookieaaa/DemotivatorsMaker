package com.nookdev.maker.dem.fragments.constructor;


import android.graphics.Bitmap;

import com.nookdev.maker.dem.models.Demotivator;

public interface ConstructorController {
    public void setImage(Bitmap bitmap);
    public Demotivator getDemotivator();
    public Bitmap getDemotivatorBitmap();
}
