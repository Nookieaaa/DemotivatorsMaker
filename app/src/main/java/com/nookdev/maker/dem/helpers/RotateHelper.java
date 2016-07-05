package com.nookdev.maker.dem.helpers;


public class RotateHelper{
    public enum RotateDirection{
        LEFT,
        RIGHT
    }

    private static final int ROTATE_STEP_ANGLE = 90;


    public static int getRotateStepAngle(RotateDirection rotateDirection){
        if(rotateDirection==null){
            return ROTATE_STEP_ANGLE;
        }
        switch (rotateDirection){
            case LEFT:{
                return -ROTATE_STEP_ANGLE;
            }
            case RIGHT:{
                return ROTATE_STEP_ANGLE;
            }
            default:{
                return 0;
            }
        }
    }
}
