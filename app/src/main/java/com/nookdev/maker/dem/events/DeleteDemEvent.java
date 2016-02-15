package com.nookdev.maker.dem.events;


import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class DeleteDemEvent {
    private Uri fileUri;
    private int position;
}
