package com.nookdev.maker.dem;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.View;

import com.nookdev.maker.dem.interfaces.MainActivityController;
import com.nookdev.maker.dem.interfaces.MainActivityView;
import com.nookdev.maker.dem.models.Demotivator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivityControllerImpl implements MainActivityController {
    private static MainActivityControllerImpl instance = new MainActivityControllerImpl();
    private MainActivityView mMainActivityView;
    private MainActivity mMainActivity;

    private MainActivityControllerImpl(){
    }

    public static MainActivityControllerImpl getInstance(){
        return instance;
    }

    @Override
    public void requestImage(int source) {
        switch (source){
            case Constants.SOURCE_GALLERY:{
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                mMainActivity.startActivityForResult(intent, Constants.PICK_IMAGE_CODE);
                break;
            }
            case Constants.SOURCE_CAMERA:{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra( MediaStore.EXTRA_OUTPUT, FileManager.getInstance().getTempFileUri());
                mMainActivity.startActivityForResult(intent, Constants.TAKE_PICTURE_CODE);
                break;
            }
        }
    }

    @Override
    public void deliverImage(int requestCode, int resultCode,Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case Constants.PICK_IMAGE_CODE: {
                try {
                    Uri selectedImage = data.getData();
                    InputStream is = mMainActivity.getContentResolver().openInputStream(selectedImage);
                    Bitmap image = Demotivator.scaleImage(BitmapFactory.decodeStream(is));
                    //fragmentImageSetter.setImage(image);
                    break;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            case Constants.TAKE_PICTURE_CODE: {
                FileManager fm = FileManager.getInstance();
                File output = new File(fm.getTempFileUri().getPath());
                if (output.exists()) {
                    Bitmap image = Demotivator.scaleImage(BitmapFactory.decodeFile(output.getAbsolutePath()));
                    //fragmentImageSetter.setImage(image);
                }
            }

        }
    }

    @Override
    public void attachFragment(Fragment fragment) {

    }

    @Override
    public void setActivity(MainActivity activity) {
        this.mMainActivity = activity;
        View v = mMainActivity.findViewById(R.id.main_activity_root);
        setView(v);
    }

    @Override
    public MainActivity getActivity() {
        return mMainActivity;
    }

    @Override
    public Bundle onSaveInstanceState(Bundle state) {
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {

    }

    public void setView(View view) {
        mMainActivityView = MainActivityViewImpl.getInstance();
        mMainActivityView.setViewWithController(view, this);
        mMainActivityView.setupViews();
    }
}
