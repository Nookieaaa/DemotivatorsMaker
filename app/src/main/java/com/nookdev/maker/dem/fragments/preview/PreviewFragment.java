package com.nookdev.maker.dem.fragments.preview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.R;

public class PreviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preview_fragment,container,false);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
