package com.nookdev.maker.dem.interfaces;


import android.net.Uri;

import java.util.List;

public interface ShareListInterface {
    public void add(Uri uri);
    public void remove(Uri uri);
    public List<Uri> getList();
    public void clear();
}
