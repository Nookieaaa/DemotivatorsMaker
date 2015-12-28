package com.nookdev.maker.dem.fragments.constructor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.R;


public class ConstructorFragment extends Fragment {
    public static final String TAG_NAME = ConstructorFragment.class.getSimpleName();
    private ConstructorController mConstructorController;



    public ConstructorFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mConstructorController = ConstructorControllerImpl.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.constructor_view, container, false);

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConstructorController.setView(view);
    }



/*
    @Override
    public void save() throws FileManager.ExternalStorageNotReadyException, FileManager.DirectoryCreationFailed {
        String caption = mDemotivatorInfo.getCaption();
        String text = mDemotivatorInfo.getText();
        Bitmap image = mDemotivatorInfo.getImage();

        Demotivator demotivator = new Demotivator(image,caption,text);

        SaveFileTask sft = new SaveFileTask();
        sft.execute(demotivator);


    }

   */

}
