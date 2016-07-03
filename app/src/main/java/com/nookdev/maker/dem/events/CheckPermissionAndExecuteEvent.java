package com.nookdev.maker.dem.events;


import lombok.Data;

@Data
public class CheckPermissionAndExecuteEvent {
    public static final int ACTION_SAVE = 12;
    public static final int ACTION_GALLERY = 13;

    private boolean success;
    private int action;
}
