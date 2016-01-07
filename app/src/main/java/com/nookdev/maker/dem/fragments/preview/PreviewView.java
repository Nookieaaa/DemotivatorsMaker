package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.view.View;

public interface PreviewView {
    public void setViewAndController(View v, PreviewController controller);
    public void setPreviewImage(Bitmap bitmap);
}
