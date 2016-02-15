package com.nookdev.maker.dem.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DemDeletedEvent {
    private boolean success;
    private int position;
}
