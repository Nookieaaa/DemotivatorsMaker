package com.nookdev.maker.dem.fragments.constructor;

import android.graphics.Bitmap;
import android.view.View;

import com.nookdev.maker.dem.models.Demotivator;

public class ConstructorControllerImpl implements ConstructorController {
    private ConstructorView mConstructorView = ConstructorViewImpl.getInstance();
    private static ConstructorControllerImpl instance = new ConstructorControllerImpl();


    private ConstructorControllerImpl(){
    };

    public static ConstructorControllerImpl getInstance(){
        return instance;
    };


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
        mConstructorView.setViewAndController(v,this);
    }

}
