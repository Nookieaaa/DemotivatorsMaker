package com.nookdev.maker.dem.fragments.constructor;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.RequestDemInfo;
import com.nookdev.maker.dem.helpers.Constants;
import com.nookdev.maker.dem.helpers.RotateHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConstructorViewImpl implements ConstructorView{

    private static ConstructorViewImpl instance = new ConstructorViewImpl();
    private ConstructorController mController;
    private int mY = 0;

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

    FloatingActionButton mFab;

    @Bind(R.id.constructor_root)
    ScrollView mRoot;

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
        Animation fadeInAnimation = AnimationUtils.loadAnimation(mImage.getContext(),R.anim.fade_out);
        Animation fadeOutAnimation = AnimationUtils.loadAnimation(mImage.getContext(),R.anim.fade_in);
        fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImage.setVisibility(View.INVISIBLE);
                mImageChanged = true;
                mOriginalBitmap = pic;
                mImage.setImageBitmap(pic);
                mImage.startAnimation(fadeInAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImage.startAnimation(fadeOutAnimation);
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
        try{
            mFab = (FloatingActionButton) v.getRootView().findViewById(R.id.fab);
        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void onRecreate() {
        mImageChanged = true;
        App.getBus().post(new RequestDemInfo());
    }

    private class RotateClickListener implements View.OnClickListener {
        private static final int ROTATE_STEP = 90;

        @Override
        public void onClick(View v) {
            RotateHelper.RotateDirection rotateDirection;
            final Matrix matrix = new Matrix();
            switch (v.getId()){

                case R.id.constructor_rotate_left:{
                    rotateDirection = RotateHelper.RotateDirection.LEFT;
                    break;
                }
                default:{
                    rotateDirection = RotateHelper.RotateDirection.RIGHT;
                    break;
                }
            }

            matrix.postRotate(RotateHelper.getRotateStepAngle(rotateDirection));
            final Bitmap b = Bitmap.createBitmap(mOriginalBitmap,
                    0,
                    0,
                    mOriginalBitmap.getWidth(),
                    mOriginalBitmap.getHeight(),
                    matrix,
                    true);
            setImage(b);
        }
    }


}
