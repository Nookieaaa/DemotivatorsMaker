package com.nookdev.maker.dem.fragments.list;


import android.content.Context;
import android.view.View;


public class GalleryControllerImpl implements GalleryController {
    private static GalleryControllerImpl instance = new GalleryControllerImpl();
    private GalleryView mGalleryView = GalleryViewImpl.getInstance();
    private Context mContext;

    private GalleryControllerImpl(){

    }

    public static GalleryControllerImpl getInstance(){
        return instance;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public void setView(View v) {
        mGalleryView.setViewAndController(v,this);
    }

}
