package com.nookdev.maker.dem.events;


import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public @Data class DeliverImageEvent {
    @NonNull private Bitmap bitmap;
    private boolean previewChanged;

}
