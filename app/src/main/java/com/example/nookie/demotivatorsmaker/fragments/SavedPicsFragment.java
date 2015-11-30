package com.example.nookie.demotivatorsmaker.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nookie.demotivatorsmaker.R;
import com.example.nookie.demotivatorsmaker.RVAdapter;
import com.example.nookie.demotivatorsmaker.interfaces.ImageSetter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedPicsFragment extends Fragment implements ImageSetter {


    @Bind(R.id.rv_pics)
    RecyclerView recyclerView;

    public SavedPicsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_pics, container, false);
        ButterKnife.bind(this,v);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RVAdapter());

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void setImage(Bitmap image) {

    }
}
