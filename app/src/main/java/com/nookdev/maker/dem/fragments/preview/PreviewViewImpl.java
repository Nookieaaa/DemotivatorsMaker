package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.nookdev.maker.dem.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PreviewViewImpl implements PreviewView{
    private static PreviewViewImpl instance = new PreviewViewImpl();
    private PreviewController mController;

    private PreviewViewImpl(){

    }

    public static PreviewViewImpl getInstance(){
        return instance;
    }

    @Bind(R.id.preview_image)
    ImageView mPreviewImage;

    @Override
    public void setViewAndController(View v, PreviewController controller) {
        ButterKnife.bind(this,v);
        mController = controller;
    }

    @Override
    public void setPreviewImage(Bitmap bitmap) {
        clearPreview();
        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(500);
        mPreviewImage.setImageBitmap(bitmap);
        mPreviewImage.startAnimation(fadeIn);
    }

    @Override
    public void clearPreview() {
        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mPreviewImage.setImageBitmap(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
