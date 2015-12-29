package com.nookdev.maker.dem.fragments.constructor;


import android.graphics.Bitmap;
import android.view.View;

import com.nookdev.maker.dem.models.Demotivator;

public interface ConstructorController {
    public void setImage(Bitmap bitmap);
    public Demotivator getDemotivator();
    public Bitmap getDemotivatorBitmap();
    public void setView(View v);
    public void requestImage();
    public void setSourceMode(int sourceMode);
}
