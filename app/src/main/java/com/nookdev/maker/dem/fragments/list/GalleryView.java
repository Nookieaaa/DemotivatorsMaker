package com.nookdev.maker.dem.fragments.list;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface GalleryView {
    public void update();
    public void setViewAndController(View v, GalleryController controller);
    public RecyclerView getmRecyclerView();
}
