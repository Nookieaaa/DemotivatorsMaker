package com.nookdev.maker.dem.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.helpers.Constants;
import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.models.Demotivator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivityControllerImpl extends BaseController implements MainActivityController {
    private static MainActivityControllerImpl instance = new MainActivityControllerImpl();
    private MainActivityView mMainActivityView;
    private MainActivity mMainActivity;
    private HashMap<String,BaseController> mControllerMap = new HashMap<>();

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
                }
            }

        }
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
    public void addController(String tag, BaseController controller) {
        mControllerMap.put(tag,controller);
    }

    @Override
    public void removeController(String tag) {
        mControllerMap.remove(tag);
    }

    @Override
    public void setView(View view) {
        mMainActivityView = MainActivityViewImpl.getInstance();
        mMainActivityView.setViewWithController(view, this);
        mMainActivityView.setupViews();
    }

    @Override
    public void sendAction(String senderTag, String receiverTag, int requestCode, Bundle data) {
        if (receiverTag.equals(MainActivity.TAG_NAME)){
            switch (requestCode){
                case Constants.PICK_IMAGE_CODE:{

                    break;
                }
                case Constants.TAKE_PICTURE_CODE:{

                    break;
                }
            }
        }
        else{
            BaseController target = mControllerMap.get(receiverTag);
            if(target!=null){
                target.sendAction(senderTag, receiverTag, requestCode, data);
            }
            else
                Log.d(MainActivity.TAG_NAME,"receiver not found!");

        }

    }

    @Override
    public void deliverResult(String senderTag, String receiverTag, int requestCode, Bundle data) {

    }
}
