package com.nookdev.maker.dem.fragments.constructor;

import android.graphics.Bitmap;
import android.view.View;

public interface ConstructorView {
    public void setImage(Bitmap bitmap);
    public Bitmap getImage();
    public String getText();
    public String getCaption();
    public void setViewAndController(View v, ConstructorController controller);
    public int getSourceMode();
}
