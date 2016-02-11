package com.nookdev.maker.dem.events;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class ImagePickEvent {
    public static final int ACTION_PICK_IMAGE = 201;
    public static final int ACTION_TAKE_PHOTO = 202;

    private int requestCode;
}
