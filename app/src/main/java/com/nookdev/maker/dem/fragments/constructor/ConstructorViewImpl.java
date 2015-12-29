package com.nookdev.maker.dem.fragments.constructor;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.helpers.Constants;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConstructorViewImpl implements ConstructorView{

    private static ConstructorViewImpl instance = new ConstructorViewImpl();
    private ConstructorController mController;

    private int mSourceMode = Constants.ACTION_PICK_IMAGE;
    private Bitmap mOriginalBitmap;
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
    }

    public static ConstructorViewImpl getInstance() {
        return instance;
    }

    private void init() {
        mImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mController.requestImage();
             }
                                 }
        );
        mRotateLeft.setOnClickListener(mRotateClickListener);
        mRotateRight.setOnClickListener(mRotateClickListener);

        mSourceSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
            }
        });

        if(mOriginalBitmap!=null)
            setImage(mOriginalBitmap);
    }


    @Override
    public void setImage(Bitmap pic) {
        mImage.setImageBitmap(pic);
        mOriginalBitmap = pic;
        mSelectImageText.setVisibility(View.GONE);
        if(mRotateLeft.getVisibility()!=View.VISIBLE){
            mRotateLeft.setVisibility(View.VISIBLE);
            mRotateRight.setVisibility(View.VISIBLE);
        }
        mImage.setBackgroundDrawable(null);
    }

    @Override
    public Bitmap getImage(Bitmap bitmap) {
        return bitmap;
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

    private class RotateClickListener implements View.OnClickListener {
        private static final int ROTATE_STEP = 90;

        @Override
        public void onClick(View v) {
            Matrix matrix = new Matrix();
            switch (v.getId()){
                case R.id.constructor_rotate_right:{
                    matrix.postRotate(ROTATE_STEP);
                    break;
                }
                case R.id.constructor_rotate_left:{
                    matrix.postRotate(360-ROTATE_STEP);
                    break;
                }
            }
            if (mOriginalBitmap !=null){

                RotateAnimation anim = new RotateAnimation(180f, 360f, 180f, 180f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.RELATIVE_TO_SELF);
                anim.setDuration(100);

                mImage.startAnimation(anim);
                setImage(Bitmap.createBitmap(mOriginalBitmap,
                        0,
                        0,
                        mOriginalBitmap.getWidth(),
                        mOriginalBitmap.getHeight(),
                        matrix,
                        true));
            }
        }

    }
}
