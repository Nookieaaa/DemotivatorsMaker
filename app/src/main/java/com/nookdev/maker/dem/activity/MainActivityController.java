package com.nookdev.maker.dem.activity;

import android.content.Intent;
import android.view.View;

public interface MainActivityController {
    void deliverImage(int requestCode, int resultCode,Intent data);
    void setActivity(MainActivity activity);
    MainActivity getActivity();
    void setView(View v);
    void fabClicked(int page);
}
