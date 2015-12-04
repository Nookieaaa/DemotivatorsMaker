package com.example.nookie.demotivatorsmaker.interfaces;


import android.net.Uri;

public interface SaveCallback {
    public void deliverSaveResult(Uri fileUri,boolean result);
}
