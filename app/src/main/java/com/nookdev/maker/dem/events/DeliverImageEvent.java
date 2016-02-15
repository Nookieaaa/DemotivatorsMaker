package com.nookdev.maker.dem.events;


import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
public @Data class DeliverImageEvent {
    @NonNull private Bitmap bitmap;

}
