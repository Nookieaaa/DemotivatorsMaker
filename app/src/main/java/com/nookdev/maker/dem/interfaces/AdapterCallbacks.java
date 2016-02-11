package com.nookdev.maker.dem.interfaces;


import android.net.Uri;

public interface AdapterCallbacks {
    void openImage(Uri uri);
    void delete(Uri uri, int position);
    void share(Uri uri);
}
