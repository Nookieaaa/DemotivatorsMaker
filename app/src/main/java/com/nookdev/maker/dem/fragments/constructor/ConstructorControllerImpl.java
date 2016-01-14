package com.nookdev.maker.dem.fragments.constructor;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.activity.MainActivity;
import com.nookdev.maker.dem.helpers.ActionMatcher;

import static com.nookdev.maker.dem.helpers.Constants.ACTION_CONSTRUCTOR_SET_IMAGE;
import static com.nookdev.maker.dem.helpers.Constants.ACTION_CREATE_PREVIEW;
import static com.nookdev.maker.dem.helpers.Constants.ACTION_DISPLAY_PREVIEW;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_CAPTION;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_IMAGE;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_TEXT;

public class ConstructorControllerImpl extends BaseController implements ConstructorController {
    private ConstructorView mConstructorView = ConstructorViewImpl.getInstance();
    private static ConstructorControllerImpl instance = new ConstructorControllerImpl();
    private int mSourceMode;


    private ConstructorControllerImpl(){
    }

    public static ConstructorControllerImpl getInstance(){
        return instance;
    }


    @Override
    public void setImage(Bitmap bitmap) {
        mConstructorView.setImage(bitmap);
    }

    @Override
    public void setView(View v) {
        mConstructorView.setViewAndController(v, this);
    }

    @Override
    public void requestImage() {
        sendAction(ConstructorFragment.TAG_NAME, MainActivity.TAG_NAME, mConstructorView.getSourceMode(), null);
    }

    @Override
    public void setSourceMode(int sourceMode) {
        mSourceMode = sourceMode;
    }

    @Override
    public void sendAction(String senderTag, String receiverTag, int requestCode, Bundle data) {
        if(receiverTag.equals(ConstructorFragment.TAG_NAME)){
            switch (requestCode){
                case ACTION_CONSTRUCTOR_SET_IMAGE:{
                    if(data.containsKey(CONTENT_IMAGE)){
                        Bitmap bitmap = data.getParcelable(CONTENT_IMAGE);
                        setImage(bitmap);
                    }
                    break;
                }
                case ACTION_CREATE_PREVIEW:{
                    if(mConstructorView.isPreviewChanged())
                    {
                        Bundle content = new Bundle();
                        content.putString(CONTENT_CAPTION, mConstructorView.getCaption());
                        content.putString(CONTENT_TEXT, mConstructorView.getText());
                        content.putParcelable(CONTENT_IMAGE, mConstructorView.getImage());
                        sendAction(ConstructorFragment.TAG_NAME, ActionMatcher.getReceiver(ACTION_DISPLAY_PREVIEW), ACTION_DISPLAY_PREVIEW, content);
                    }
                }
            }
        }
        else
            getActivityController().sendAction(senderTag, receiverTag, requestCode,data);
    }

}
