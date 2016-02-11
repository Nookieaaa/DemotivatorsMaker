package com.nookdev.maker.dem.fragments.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.R;

public class GalleryFragment extends Fragment {

    public static final String TAG_NAME = GalleryFragment.class.getSimpleName();

    private GalleryController mController = GalleryControllerImpl.getInstance();


    public GalleryFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mController.refresh();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_pics, container, false);

        mController.setView(v);
        mController.setContext(getActivity());
        return v;
    }
}
