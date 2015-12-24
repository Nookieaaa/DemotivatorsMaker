package com.nookdev.maker.dem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nookdev.maker.dem.activity.MainActivity;

public interface MainActivityController {
    public void requestImage(int source);
    public void deliverImage(int requestCode, int resultCode,Intent data);
    public void attachFragment(Fragment fragment);
    public void setActivity(MainActivity activity);
    public MainActivity getActivity();
    public Bundle onSaveInstanceState(Bundle state);
    public void onRestoreInstanceState(Bundle state);


}
