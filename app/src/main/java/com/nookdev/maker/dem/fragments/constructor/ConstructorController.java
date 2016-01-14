package com.nookdev.maker.dem.fragments.constructor;


import android.graphics.Bitmap;
import android.view.View;

public interface ConstructorController {
    public void setImage(Bitmap bitmap);
    public void setView(View v);
    public void requestImage();
    public void setSourceMode(int sourceMode);
}
