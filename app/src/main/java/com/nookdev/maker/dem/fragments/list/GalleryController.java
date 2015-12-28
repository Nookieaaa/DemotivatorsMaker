package com.nookdev.maker.dem.fragments.list;


import android.content.Context;
import android.view.View;

public interface GalleryController {
    public void refresh();
    public void setView(View v);
    public Context getContext();
    public void setContext(Context context);
}
