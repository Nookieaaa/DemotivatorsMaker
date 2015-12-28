package com.nookdev.maker.dem.fragments.constructor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.interfaces.FragmentController;


public class ConstructorFragment extends Fragment implements FragmentController {
    public static final String TAG_NAME = ConstructorFragment.class.getSimpleName();
    private ConstructorController mController = ConstructorControllerImpl.getInstance();;



    public ConstructorFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.constructor_view, container, false);

        return v;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController.setView(view);
    }

    @Override
    public BaseController getController() {
        return (BaseController) mController;
    }

    @Override
    public String getFragmentTag() {
        return TAG_NAME;
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
