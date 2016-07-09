package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;

public interface PreviewView {
    void setViewAndController(View v, PreviewController controller);
    void setPreviewImage(Bitmap bitmap);
    void setPreviewImage(Uri uri);
    void clearPreview();
}
