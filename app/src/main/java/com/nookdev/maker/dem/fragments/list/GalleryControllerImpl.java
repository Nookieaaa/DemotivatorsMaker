package com.nookdev.maker.dem.fragments.list;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.nookdev.maker.dem.BaseController;


public class GalleryControllerImpl extends BaseController implements GalleryController {
    private static GalleryControllerImpl instance = new GalleryControllerImpl();
    private GalleryView mGalleryView = GalleryViewImpl.getInstance();
    private Context mContext;

    private GalleryControllerImpl(){

    }

    public static GalleryControllerImpl getInstance(){
        return instance;
    }

    @Override
    public void refresh() {

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
    public void sendAction(String senderTag, String receiverTag, int requestCode, Bundle data) {

    }

    @Override
    public void deliverResult(String senderTag, String receiverTag, int requestCode, Bundle data) {

    }

    @Override
    public void registerMainController(BaseController bc) {

    }

    @Override
    public void setView(View v) {
        mGalleryView.setViewAndController(v,this);
    }
}
