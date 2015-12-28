package com.nookdev.maker.dem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.interfaces.FragmentController;

public class MainActivity extends AppCompatActivity {
    public static final String TAG_NAME = MainActivity.class.getSimpleName();
    MainActivityController mController;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        try{
            FragmentController fc = (FragmentController)fragment;
            BaseController bc = fc.getController();
            bc.registerMainController((BaseController)mController);
            mController.addController(fc.getFragmentTag(),bc);
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = MainActivityControllerImpl.getInstance();
        mController.setActivity(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mController.deliverImage(requestCode,resultCode,data);

    }

}
