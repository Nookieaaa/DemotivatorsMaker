package com.example.nookie.demotivatorsmaker.views;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.nookie.demotivatorsmaker.ImagePicker;
import com.example.nookie.demotivatorsmaker.ImageSetter;
import com.example.nookie.demotivatorsmaker.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ConstructorView extends FrameLayout implements ImageSetter{

    private ImagePicker mFragmentImagePicker;

    private int sourceMode;


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
        //Bitmap dem = createBitmap(image);
        image.setImageBitmap(pic);
        selectImageText.setVisibility(GONE);
    }

    private Bitmap createBitmap(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();

        Bitmap dem = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dem);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);




        Bitmap temp = Bitmap.createScaledBitmap(image, width / 2, height / 2, false);

        float bmLeft = width/2 - temp.getWidth()/2;
        float bmTop = height/2 - temp.getHeight()/2;

        canvas.drawBitmap(temp, bmLeft, bmTop, paint);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        Rect r = new Rect((int)bmLeft - 10,(int)bmTop -10, (int)bmLeft + temp.getWidth() + 10, (int)bmTop + temp.getHeight()+10);

        canvas.drawRect(r, paint);

        paint.setTextSize(10);
        //canvas.drawText(caption.getText().toString(),);

        return dem;
    }
}
