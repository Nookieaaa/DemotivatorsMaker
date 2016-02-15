package com.nookdev.maker.dem.events;


import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class DemSavedEvent {
    Uri fileUri;
    Throwable error;
    boolean success;
}
