package com.nookdev.maker.dem.interfaces;


import android.net.Uri;

public interface SaveCallback {
    public void deliverSaveResult(Uri fileUri,boolean result);
}
