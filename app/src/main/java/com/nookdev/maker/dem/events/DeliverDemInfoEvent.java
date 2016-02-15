package com.nookdev.maker.dem.events;


import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@AllArgsConstructor
public @Data class DeliverDemInfoEvent {
    @NonNull private String caption;
    @NonNull private String text;
    private Bitmap image;
    private boolean isChanged;
}
