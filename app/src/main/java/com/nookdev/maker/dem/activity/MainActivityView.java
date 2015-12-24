package com.nookdev.maker.dem.activity;


import android.view.View;

public interface MainActivityView {
    public void setupViews();
    public void setViewWithController(View v, MainActivityController controller);
}
