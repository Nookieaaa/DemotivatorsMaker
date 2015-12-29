package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.models.Demotivator;

import static com.nookdev.maker.dem.helpers.Constants.ACTION_MAKE_PREVIEW;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_CAPTION;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_IMAGE;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_TEXT;

public class PreviewControllerImpl extends BaseController implements PreviewController {

    private Demotivator mDemotivator;

    @Override
    public void sendAction(String senderTag, String receiverTag, int requestCode, Bundle data) {
        if(receiverTag.equals(PreviewFragment.TAG_NAME)){
            switch (requestCode){
                case ACTION_MAKE_PREVIEW:{
                    String caption;
                    String text;
                    Bitmap image=null;

                    if(data.containsKey(CONTENT_IMAGE))
                        image = data.getParcelable(CONTENT_IMAGE);

                    if(data.containsKey(CONTENT_TEXT))
                        text = data.getString(CONTENT_TEXT);
                    else
                        text="";

                    if(data.containsKey(CONTENT_CAPTION))
                        caption = data.getString(CONTENT_CAPTION);
                    else
                        caption="";

                    mDemotivator = new Demotivator(image,caption,text);


                    //Demotivator demotivator = new Demotivator()
                    break;
                }
            }
        }
    }

    @Override
    public void setView(View v) {

    }
}
