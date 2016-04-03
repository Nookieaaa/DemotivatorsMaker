package com.nookdev.maker.dem.fragments.list;


import android.content.Context;
import android.view.View;

public interface GalleryController {
    Context getContext();
    void setContext(Context context);
    void setView(View v);
}
