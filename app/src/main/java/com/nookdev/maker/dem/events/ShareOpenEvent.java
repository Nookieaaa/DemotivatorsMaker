package com.nookdev.maker.dem.events;

import android.net.Uri;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShareOpenEvent {
    private Uri uri;
    private boolean share;
}
