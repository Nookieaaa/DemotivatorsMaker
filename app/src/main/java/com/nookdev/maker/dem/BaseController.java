package com.nookdev.maker.dem;


import android.os.Bundle;

public abstract class BaseController {
    private BaseController mActivityController;

    public abstract void sendAction(String senderTag, String receiverTag, int requestCode, Bundle data);

    public abstract void deliverResult(String senderTag, String receiverTag, int requestCode, Bundle data);

    public void registerMainController(BaseController bc){
        mActivityController = bc;
    };

    public BaseController getActivityController(){
        return mActivityController;
    }
}
