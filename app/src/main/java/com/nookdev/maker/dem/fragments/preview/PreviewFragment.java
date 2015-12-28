package com.nookdev.maker.dem.fragments.preview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.BaseController;
import com.nookdev.maker.dem.interfaces.FragmentController;

public class PreviewFragment extends Fragment implements FragmentController {
    public static final String TAG_NAME = PreviewFragment.class.getSimpleName();
    private PreviewController mController = new PreviewControllerImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preview_fragment,container,false);
        return v;
    }

    @Override
    public BaseController getController() {
        return (BaseController)mController;
    }

    @Override
    public String getFragmentTag() {
        return TAG_NAME;
    }
}
