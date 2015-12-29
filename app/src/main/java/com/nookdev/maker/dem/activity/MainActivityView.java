package com.nookdev.maker.dem.activity;


import android.view.View;

public interface MainActivityView {
    void setupViews();
    void selectFragment(int page);
    void setViewWithController(View v, MainActivityController controller);
}
