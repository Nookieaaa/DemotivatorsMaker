package com.nookdev.maker.dem.events;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class ImagePickEvent {
    private int requestCode;
}
