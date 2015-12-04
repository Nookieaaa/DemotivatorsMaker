package com.example.nookie.demotivatorsmaker.interfaces;


import android.net.Uri;

public interface AdapterCallbacks {
    public void openImage(Uri uri);
    public void delete(Uri uri, int position);
    public void share(Uri uri);
}
