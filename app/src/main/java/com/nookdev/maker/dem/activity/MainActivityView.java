package com.nookdev.maker.dem.activity;


import android.view.View;

import com.nookdev.maker.dem.events.DemSavedEvent;

public interface MainActivityView {
    void setupViews();
    void selectFragment(int page);
    void setViewWithController(View v, MainActivityController controller);
    void notifySaveResult(DemSavedEvent event);
}
