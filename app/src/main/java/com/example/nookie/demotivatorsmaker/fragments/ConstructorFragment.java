package com.example.nookie.demotivatorsmaker.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nookie.demotivatorsmaker.MainActivity;
import com.example.nookie.demotivatorsmaker.R;
import com.example.nookie.demotivatorsmaker.interfaces.ImagePicker;
import com.example.nookie.demotivatorsmaker.interfaces.ImageSetter;
import com.example.nookie.demotivatorsmaker.models.Demotivator;
import com.example.nookie.demotivatorsmaker.views.ConstructorView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ConstructorFragment extends Fragment implements ImagePicker, ImageSetter, MainActivity.DemotivatorSaver {

    private ImagePicker mActivityImagePicker;
    private ImageSetter mViewImageSetter;
    private DemotivatorInfo mDemotivatorInfo;

    public ConstructorFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mActivityImagePicker = (ImagePicker)context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConstructorView v = (ConstructorView)view.findViewById(R.id.constructor_view);
        mViewImageSetter = (ImageSetter)v;
        mDemotivatorInfo = (DemotivatorInfo)v;

    }

    @Override
    public void pickImage(int sourceMode) {
        mActivityImagePicker.pickImage(sourceMode);
    }

    @Override
    public void setImage(Bitmap image) {
        mViewImageSetter.setImage(image);
    }

    @Override
    public Uri save() {
        String caption = mDemotivatorInfo.getCaption();
        String text = mDemotivatorInfo.getText();
        Bitmap image = mDemotivatorInfo.getImage();

        Demotivator demotivator = new Demotivator(image,caption,text);
        Bitmap result = demotivator.toBitmap();

        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"testdem.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            result.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Uri.fromFile(f);
    }

    public interface DemotivatorInfo{
        public String getText();
        public String getCaption();
        public Bitmap getImage();
    }
}
