package com.example.nookie.demotivatorsmaker.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nookie.demotivatorsmaker.R;
import com.example.nookie.demotivatorsmaker.fragments.ConstructorFragment;
import com.example.nookie.demotivatorsmaker.interfaces.ImagePicker;
import com.example.nookie.demotivatorsmaker.interfaces.ImageSetter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConstructorView extends FrameLayout implements ImageSetter, ConstructorFragment.DemotivatorInfo{

    private ImagePicker mFragmentImagePicker;

    private int sourceMode;
    private Bitmap originalBitmap;


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

    public ConstructorView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ConstructorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ConstructorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attributeSet, int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.constructor_view, null);
        addView(v);

        mFragmentImagePicker = (ImagePicker)context;

        ButterKnife.bind(this);

        image.setOnClickListener(new OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         mFragmentImagePicker.pickImage(sourceMode);
                                     }
                                 }
        );

        sourceSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.constructor_rb_camera: {
                        sourceMode = ImagePicker.SOURCE_CAMERA;
                        break;
                    }
                    case R.id.constructor_rb_gallery: {
                        sourceMode = ImagePicker.SOURCE_GALLERY;
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
        selectImageText.setVisibility(GONE);
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

}
