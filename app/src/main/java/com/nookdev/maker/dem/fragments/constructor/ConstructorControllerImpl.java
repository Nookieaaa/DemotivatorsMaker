package com.nookdev.maker.dem.fragments.constructor;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.activity.MainActivity;
import com.nookdev.maker.dem.helpers.Constants;
import com.nookdev.maker.dem.models.Demotivator;

public class ConstructorControllerImpl extends BaseController implements ConstructorController {
    private ConstructorView mConstructorView = ConstructorViewImpl.getInstance();
    private static ConstructorControllerImpl instance = new ConstructorControllerImpl();


    private ConstructorControllerImpl(){
    }

    public static ConstructorControllerImpl getInstance(){
        return instance;
    }


    @Override
    public void setImage(Bitmap bitmap) {
        mConstructorView.setImage(bitmap);
    }

    @Override
    public Demotivator getDemotivator() {
        return null;
    }

    @Override
    public Bitmap getDemotivatorBitmap() {
        return null;
    }

    @Override
    public void setView(View v) {
        mConstructorView.setViewAndController(v, this);
    }

    @Override
    public void requestImage() {
        sendAction(ConstructorFragment.TAG_NAME, MainActivity.TAG_NAME, Constants.ACTION_PICK_IMAGE, null);
    }

    @Override
    public void sendAction(String senderTag, String receiverTag, int requestCode, Bundle data) {
        getActivityController().sendAction(senderTag, receiverTag, requestCode,data);
    }

}
