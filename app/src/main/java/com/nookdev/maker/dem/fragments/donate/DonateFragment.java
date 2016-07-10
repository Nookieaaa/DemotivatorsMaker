package com.nookdev.maker.dem.fragments.donate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.R;

public class DonateFragment extends Fragment {
    private DonateController mDonateController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ads_fragment,container,false);
        mDonateController = new DonateController();
        mDonateController.setView(v);
        return v;
    }
}
