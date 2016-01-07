package com.nookdev.maker.dem.fragments.preview;


import android.graphics.Bitmap;
import android.view.View;
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
        mPreviewImage.setImageBitmap(bitmap);
    }
}
