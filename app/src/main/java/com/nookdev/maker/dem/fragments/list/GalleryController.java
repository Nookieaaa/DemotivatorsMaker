package com.nookdev.maker.dem.fragments.list;


import android.content.Context;
import android.view.View;

public interface GalleryController {
    public void refresh();
    public Context getContext();
    public void setContext(Context context);
    public void setView(View v);
}
