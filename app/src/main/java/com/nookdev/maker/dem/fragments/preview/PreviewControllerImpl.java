package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.models.Demotivator;

import static com.nookdev.maker.dem.helpers.Constants.ACTION_DISPLAY_PREVIEW;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_CAPTION;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_IMAGE;
import static com.nookdev.maker.dem.helpers.Constants.CONTENT_TEXT;

public class PreviewControllerImpl extends BaseController implements PreviewController {

    private static PreviewControllerImpl instance = new PreviewControllerImpl();
    private PreviewView mPreviewView;
    private Bitmap mImage;

    private PreviewControllerImpl(){

    }

    public static PreviewControllerImpl getInstance(){
        return instance;
    }

    @Override
    public void sendAction(String senderTag, String receiverTag, int requestCode, Bundle data) {
        if(receiverTag.equals(PreviewFragment.TAG_NAME)){
            switch (requestCode){
                case ACTION_DISPLAY_PREVIEW:{
                    String caption;
                    String text;
                    Bitmap image=null;

                    if (data==null)
                        return;

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

                    updatePreview(caption,text,image);

                    break;
                }
            }
        }
    }

    public void updatePreview(String caption, String text, Bitmap image){

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what==1){
                    mPreviewView.setPreviewImage(mImage);
                }
                return false;
            }
        });

        PreviewRunnable run = new PreviewRunnable();
        run.setData(caption,text,image);
        run.setHandler(handler);
        new Thread(run).start();
    }

    @Override
    public void setView(View v) {
        mPreviewView = PreviewViewImpl.getInstance();
        mPreviewView.setViewAndController(v,this);
        if(mImage!=null){
            mPreviewView.setPreviewImage(mImage);
        }
    }

    private class PreviewRunnable implements Runnable{
        private String tCaption;
        private String tText;
        private Bitmap tImage;
        private Handler handler;

        public void setData(String caption, String text, Bitmap image){
            tCaption = caption;
            tText = text;
            tImage = image;
        }

        public void setHandler(Handler handler){
            this.handler = handler;
        }

        @Override
        public void run() {
            Demotivator dem = new Demotivator(tImage,tCaption,tText);
            mImage = dem.toBitmap();
            handler.sendEmptyMessage(1);
        }
    }
}
