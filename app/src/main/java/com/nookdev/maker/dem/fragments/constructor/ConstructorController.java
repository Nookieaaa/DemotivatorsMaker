package com.nookdev.maker.dem.fragments.constructor;


import android.view.View;

public interface ConstructorController {
    void setView(View v);
    void requestImage(int sourceMode);
    void onRecreate();
}
