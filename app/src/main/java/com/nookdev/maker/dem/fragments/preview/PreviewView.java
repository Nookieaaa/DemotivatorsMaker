package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.view.View;

public interface PreviewView {
    void setViewAndController(View v, PreviewController controller);
    void setPreviewImage(Bitmap bitmap);
    void clearPreview();
}
