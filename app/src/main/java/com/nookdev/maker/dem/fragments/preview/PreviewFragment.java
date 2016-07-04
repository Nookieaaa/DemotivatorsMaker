package com.nookdev.maker.dem.fragments.preview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;

public class PreviewFragment extends Fragment {
    public static final String TAG_NAME = PreviewFragment.class.getSimpleName();
    private PreviewController mController = PreviewControllerImpl.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preview_fragment,container,false);
        App.getBus().register(mController);
        mController.setView(v);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        App.getBus().unregister(mController);
    }
}
