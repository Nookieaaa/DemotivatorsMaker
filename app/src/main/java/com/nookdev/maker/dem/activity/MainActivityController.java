package com.nookdev.maker.dem.activity;

import android.content.Intent;
import android.view.View;

import com.nookdev.maker.dem.BaseController;

public interface MainActivityController {
    public void deliverImage(int requestCode, int resultCode,Intent data);
    public void setActivity(MainActivity activity);
    public MainActivity getActivity();
    public void addController(String tag,BaseController controller);
    public void removeController(String tag);
    public void setView(View v);
    public void fabClicked(int page);
    public void createPreview();
}
