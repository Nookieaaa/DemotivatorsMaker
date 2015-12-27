package com.nookdev.maker.dem.fragments.constructor;

import android.graphics.Bitmap;

public interface ConstructorView {
    public void setImage(Bitmap bitmap);
    public void getImage(Bitmap bitmap);
    public String getText(String text);
    public String getCaption(String caption);
}
