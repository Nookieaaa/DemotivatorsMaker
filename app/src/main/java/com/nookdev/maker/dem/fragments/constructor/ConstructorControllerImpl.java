package com.nookdev.maker.dem.fragments.constructor;

import android.view.View;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.events.DeliverDemInfoEvent;
import com.nookdev.maker.dem.events.DeliverImageEvent;
import com.nookdev.maker.dem.events.ImagePickEvent;
import com.nookdev.maker.dem.events.RequestDemInfo;
import com.nookdev.maker.dem.events.SaveDemEvent;
import com.nookdev.maker.dem.helpers.FileManager;
import com.squareup.otto.Subscribe;

public class ConstructorControllerImpl implements ConstructorController {
    private ConstructorView mConstructorView = ConstructorViewImpl.getInstance();
    private static ConstructorControllerImpl instance = new ConstructorControllerImpl();

    private ConstructorControllerImpl(){
        App.getBus().register(this);
    }

    public static ConstructorControllerImpl getInstance(){
        return instance;
    }

    @Override
    public void setView(View v) {
        mConstructorView.setViewAndController(v, this);
    }

    @Override
    public void requestImage(int sourceMode) {
        App.getBus().post(new ImagePickEvent(mConstructorView.getSourceMode()));
    }

    @Override
    public void onRecreate() {
        mConstructorView.onRecreate();
    }

    @Subscribe
    public void onImageDelivered(DeliverImageEvent event){
        mConstructorView.setImage(event.getBitmap());
    }

    @Subscribe
    public void onRequestDemInfo(RequestDemInfo event){
        sendDemInfo();
    }

    private void sendDemInfo(){
        App.getBus()
                .post(new DeliverDemInfoEvent(
                        mConstructorView.getCaption(),
                        mConstructorView.getText(),
                        mConstructorView.getImage(),
                        mConstructorView.isPreviewChanged()
                ));
    }

    @Subscribe
    public void saveDem(SaveDemEvent event){
        FileManager fm = FileManager.getInstance();
        fm.saveDem(mConstructorView.getCaption(),
                mConstructorView.getText(),
                mConstructorView.getImage());
    }

}
