package com.nookdev.maker.dem.fragments.constructor;

import android.graphics.Bitmap;
import android.view.View;

public interface ConstructorView {
    void setImage(Bitmap bitmap);
    Bitmap getImage();
    String getText();
    String getCaption();
    void setViewAndController(View v, ConstructorController controller);
    int getSourceMode();
    boolean isPreviewChanged();
    void onRecreate();
}
