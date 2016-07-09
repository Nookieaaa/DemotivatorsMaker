package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.view.View;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.events.DeliverDemInfoEvent;
import com.nookdev.maker.dem.events.RequestDemInfo;
import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.models.Demotivator;
import com.squareup.otto.Subscribe;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PreviewControllerImpl implements PreviewController {

    private static PreviewControllerImpl instance = new PreviewControllerImpl();
    private PreviewView mPreviewView;
    private Bitmap mImage;

    private PreviewControllerImpl(){

    }

    public static PreviewControllerImpl getInstance(){
        return instance;
    }

    public void updatePreview(String caption, String text, Bitmap image, boolean changed){
        if (!changed)
            return;
        Observable.just(new Demotivator(image,caption,text))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(Demotivator::toBitmap)
                .map(FileManager.getInstance()::cachePreview)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mPreviewView::setPreviewImage, Throwable::printStackTrace);
    }

    @Override
    public void setView(View v) {
        mPreviewView = PreviewViewImpl.getInstance();
        mPreviewView.setViewAndController(v, this);
        if(mImage!=null){
            mPreviewView.setPreviewImage(mImage);
        }

    }

    @Override
    public void requestDemInfo() {
        App.getBus().post(new RequestDemInfo());
    }

    @Subscribe
    public void onDemInfoReceived(DeliverDemInfoEvent event){
        updatePreview(
                event.getCaption(),
                event.getText(),
                event.getImage(),
                event.isChanged()
        );
    }


}
