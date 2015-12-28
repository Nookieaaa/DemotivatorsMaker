package com.nookdev.maker.dem.fragments.list;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nookdev.maker.dem.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryViewImpl implements GalleryView {
    private static GalleryViewImpl instance = new GalleryViewImpl();
    private GalleryController mController;
    private RVAdapter mRVAdapter;

    private GalleryViewImpl(){

    }

    public static GalleryViewImpl getInstance(){
        return instance;
    }

    @Bind(R.id.rv_pics)
    RecyclerView mRecyclerView;

    @Override
    public void update() {

    }

    @Override
    public void setViewAndController(View v, GalleryController controller) {
        mController = controller;
        ButterKnife.bind(this,v);
        init();
    }

    private void init() {
        mRVAdapter = new RVAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(mController.getContext(), 2, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mRVAdapter);
    }

    @Override
    public RecyclerView getmRecyclerView() {
        return mRecyclerView;
    }
}
