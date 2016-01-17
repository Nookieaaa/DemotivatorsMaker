package com.nookdev.maker.dem.helpers;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;

import com.nookdev.maker.dem.R;

public class RotateImageAnimation extends Animation{
    public enum RotateDirection{
        LEFT,
        RIGHT
    }
    int mRotateAngle;
    ImageButton mView;
    Bitmap mImage;
    int mTargetHeight;
    int mInitialHeight;
    int mTargetWidth;
    int mInitialWidth;
    int mMaxHeight;
    int mMaxWidth;
    float scale;

    private static final int ROTATE_STEP_ANGLE = 90;

    public RotateImageAnimation(ImageButton v, Bitmap bitmap, RotateDirection direction) {
        if(direction==null){
            direction = RotateDirection.RIGHT;
        }
        mImage = bitmap;
        mView = v;
        mRotateAngle = getRotateStepAngle(direction);
        calculateTranslation();
    }

    private void calculateTranslation(){
        mMaxHeight = (int)App.getAppContext()
                .getResources()
                .getDimension(R.dimen.constructor_image_max_height);

        mMaxWidth = App.resolveDisplayMetrics().widthPixels;

        mInitialHeight = Math.min(mView.getHeight(),mMaxHeight);
        mInitialWidth = Math.min(mView.getWidth(),mMaxWidth);

        int imageHeight = mImage.getHeight();
        int imageWidth = mImage.getWidth();
        //int imageHeight = mImage.getWidth();
        //int imageWidth = mImage.getHeight();

        mTargetHeight = Math.min(imageHeight, mMaxHeight);
        mTargetWidth = Math.min(imageWidth,mMaxWidth);

       // if (imageHeight > imageWidth){
            scale = (float)mTargetWidth / (float)imageHeight;
            mTargetWidth = (int)(imageWidth * scale);
        int b = mTargetHeight;
        mTargetHeight = mTargetWidth;
        mTargetWidth = b;
        //}
        //else{
        //scale = (float)mTargetWidth / (float)imageWidth;
        //mTargetHeight = (int)(imageHeight*scale);
       // }
    }

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

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        int newHeight = (int) (mInitialHeight + (mTargetHeight - mInitialHeight) * interpolatedTime);
        int newWidth = (int) (mInitialWidth + (mTargetWidth - mInitialWidth) * interpolatedTime);


        Matrix matrix = t.getMatrix();
        //matrix.postRotate(mRotateAngle * interpolatedTime, newWidth * 0.5f, newHeight * 0.5f);
        matrix.postRotate(mRotateAngle * interpolatedTime, newWidth * 0.5f, newHeight * 0.5f);
        //matrix.postScale(scale,scale,mInitialWidth/2,mInitialHeight/2);


        mView.getLayoutParams().height = newHeight;
        mView.getLayoutParams().width =  newWidth;
        mView.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return false;
    }
}
