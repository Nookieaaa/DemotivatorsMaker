package com.nookdev.maker.dem.fragments.constructor;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.helpers.Constants;
import com.nookdev.maker.dem.helpers.RotateImageAnimation;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConstructorViewImpl implements ConstructorView{

    private static ConstructorViewImpl instance = new ConstructorViewImpl();
    private ConstructorController mController;

    private int mSourceMode = Constants.ACTION_PICK_IMAGE;
    private Bitmap mOriginalBitmap;
    private String mCaptionString;
    private String mTextString;
    private boolean mImageChanged = false;

    private final RotateClickListener mRotateClickListener = new RotateClickListener();

    @Bind(R.id.constructor_radiogroup)
    RadioGroup mSourceSelector;

    @Bind(R.id.constructor_image)
    ImageButton mImage;

    @Bind(R.id.constructor_select_image_text)
    TextView mSelectImageText;

    @Bind(R.id.constructor_caption)
    EditText mCaption;

    @Bind(R.id.constructor_text)
    EditText mText;

    @Bind(R.id.constructor_rotate_left)
    ImageButton mRotateLeft;

    @Bind(R.id.constructor_rotate_right)
    ImageButton mRotateRight;

    private ConstructorViewImpl() {
        App.getBus().register(this);
    }

    public static ConstructorViewImpl getInstance() {
        return instance;
    }

    private void init() {
        mImage.setOnClickListener(v -> mController.requestImage(mSourceMode));
        mRotateLeft.setOnClickListener(mRotateClickListener);
        mRotateRight.setOnClickListener(mRotateClickListener);

        mSourceSelector.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.constructor_rb_camera: {
                    mSourceMode = Constants.ACTION_TAKE_PHOTO;
                    mSelectImageText.setText(R.string.select_image_camera);
                    break;
                }
                case R.id.constructor_rb_gallery: {
                    mSourceMode = Constants.ACTION_PICK_IMAGE;
                    mSelectImageText.setText(R.string.select_image_gallery);
                    break;
                }
            }
        });

        if(mOriginalBitmap!=null){
            setImage(mOriginalBitmap);
            mImageChanged = false;
        }
    }


    @Override
    public void setImage(Bitmap pic) {
        mImageChanged = true;
        mOriginalBitmap = pic;
        mImage.setImageBitmap(pic);
        mSelectImageText.setVisibility(View.GONE);
        if(mRotateLeft.getVisibility()!=View.VISIBLE){
            mRotateLeft.setVisibility(View.VISIBLE);
            mRotateRight.setVisibility(View.VISIBLE);
        }
        mImage.setBackgroundDrawable(null);
    }

    @Override
    public Bitmap getImage() {
        return mOriginalBitmap;
    }

    @Override
    public String getText() {
        return mText.getText().toString();
    }

    @Override
    public String getCaption() {
        return mCaption.getText().toString();
    }

    @Override
    public void setViewAndController(View v, ConstructorController controller) {
        mController = controller;
        ButterKnife.bind(this, v);
        init();
    }

    @Override
    public int getSourceMode() {
        return mSourceMode;
    }

    @Override
    public boolean isPreviewChanged() {
        if(mImageChanged){
            mImageChanged = false;
            return true;
        }
        else{
            String currText = getText();
            String currCaption = getCaption();

            boolean changes = !(currCaption.equals(mCaptionString)&&currText.equals(mTextString));

            mTextString = currText;
            mCaptionString = currCaption;

            return changes;
        }
    }

    private class RotateClickListener implements View.OnClickListener {
        private static final int ROTATE_STEP = 90;

        @Override
        public void onClick(View v) {
            RotateImageAnimation.RotateDirection rotateDirection;
            final Matrix matrix = new Matrix();
            switch (v.getId()){

                case R.id.constructor_rotate_left:{
                    rotateDirection = RotateImageAnimation.RotateDirection.LEFT;
                    break;
                }
                default:{
                    rotateDirection = RotateImageAnimation.RotateDirection.RIGHT;
                    break;
                }
            }

            matrix.postRotate(RotateImageAnimation.getRotateStepAngle(rotateDirection));
            if (mOriginalBitmap !=null){


                RotateImageAnimation animation = new RotateImageAnimation(mImage,mOriginalBitmap,rotateDirection);
                animation.setInterpolator(new LinearInterpolator());
                animation.setDuration(5000);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        final Bitmap b = Bitmap.createBitmap(mOriginalBitmap,
                        0,
                        0,
                        mOriginalBitmap.getWidth(),
                        mOriginalBitmap.getHeight(),
                        matrix,
                        true);
                        setImage(b);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mImage.startAnimation(animation);

            }
        }
    }
}
