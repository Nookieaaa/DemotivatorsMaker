package com.nookdev.maker.dem.events;


import lombok.Data;

@Data
public class CheckPermissionAndExecuteEvent {
    private boolean success;
    private int action;
}
