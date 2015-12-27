package com.nookdev.maker.dem.fragments.constructor;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.interfaces.ImagePicker;
import com.nookdev.maker.dem.interfaces.ImageSetter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConstructorViewImpl implements ImageSetter, ConstructorFragment.DemotivatorInfo{

    private ImagePicker mFragmentImagePicker;

    private int sourceMode;
    private Bitmap originalBitmap;
    private final RotateClickListener rotateClickListener = new RotateClickListener();


    @Bind(R.id.constructor_radiogroup)
    RadioGroup sourceSelector;

    @Bind(R.id.constructor_frame)
    FrameLayout frame;

    @Bind(R.id.constructor_image)
    ImageButton image;

    @Bind(R.id.constructor_select_image_text)
    TextView selectImageText;

    @Bind(R.id.constructor_caption)
    EditText caption;

    @Bind(R.id.constructor_text)
    EditText text;

    @Bind(R.id.constructor_rotate_left)
    ImageButton rotateLeft;

    @Bind(R.id.constructor_rotate_right)
    ImageButton rotateRight;

    private void init(Context context, AttributeSet attributeSet, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.constructor_view, null);

        mFragmentImagePicker = (ImagePicker)context;

        ButterKnife.bind(this,v);

        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         mFragmentImagePicker.pickImage(sourceMode);
                                     }
                                 }
        );
        rotateLeft.setOnClickListener(rotateClickListener);
        rotateRight.setOnClickListener(rotateClickListener);



        sourceSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.constructor_rb_camera: {
                        sourceMode = ImagePicker.SOURCE_CAMERA;
                        selectImageText.setText(R.string.select_image_camera);
                        break;
                    }
                    case R.id.constructor_rb_gallery: {
                        sourceMode = ImagePicker.SOURCE_GALLERY;
                        selectImageText.setText(R.string.select_image_gallery);
                        break;
                    }
                }
            }
        });
    }


    @Override
    public void setImage(Bitmap pic) {
        image.setImageBitmap(pic);
        originalBitmap = pic;
        selectImageText.setVisibility(View.GONE);
        if(rotateLeft.getVisibility()!=View.VISIBLE){
            rotateLeft.setVisibility(View.VISIBLE);
            rotateRight.setVisibility(View.VISIBLE);
        }
    }

    public String getCaption(){
        return caption.getText().toString();
    }

    public String getText(){
        return text.getText().toString();
    }

    public Bitmap getImage(){
        return originalBitmap;
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
            if (originalBitmap!=null){

                RotateAnimation anim = new RotateAnimation(180f, 360f, 180f, 180f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.RELATIVE_TO_SELF);
                anim.setDuration(100);

                image.startAnimation(anim);
                setImage(Bitmap.createBitmap(originalBitmap,
                        0,
                        0,
                        originalBitmap.getWidth(),
                        originalBitmap.getHeight(),
                        matrix,
                        true));
            }
        }

    }
}
