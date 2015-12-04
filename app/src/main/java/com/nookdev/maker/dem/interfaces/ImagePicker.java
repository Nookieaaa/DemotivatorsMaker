package com.nookdev.maker.dem.interfaces;


public interface ImagePicker {
    public static final int SOURCE_GALLERY = 0;
    public static final int SOURCE_CAMERA = 1;
    public void pickImage(int sourceType);
}
