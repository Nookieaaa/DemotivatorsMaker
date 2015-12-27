package com.nookdev.maker.dem.fragments.constructor;

import android.graphics.Bitmap;

import com.nookdev.maker.dem.models.Demotivator;

public class ConstructorControllerImpl implements ConstructorController {
    private ConstructorView mConstructorView;
    private static ConstructorControllerImpl instance = new ConstructorControllerImpl();
    private ConstructorControllerImpl(){};

    public static ConstructorControllerImpl getInstance(){
        return instance;
    };


    @Override
    public void setImage(Bitmap bitmap) {

    }

    @Override
    public Demotivator getDemotivator() {
        return null;
    }

    @Override
    public Bitmap getDemotivatorBitmap() {
        return null;
    }
}
