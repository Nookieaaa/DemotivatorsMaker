package com.nookdev.maker.dem.fragments.list;


import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.RefreshEvent;

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

    @Bind(R.id.empty_list)
    CardView mEmptyList;

    @Override
    public void update() {
        mEmptyList.setVisibility(
                mRVAdapter.getItemCount()>0 ? View.GONE : View.VISIBLE
        );
    }

    @Override
    public void setViewAndController(View v, GalleryController controller) {
        mController = controller;
        ButterKnife.bind(this, v);
        init();
    }

    private void init() {
        if(mRVAdapter==null)
            mRVAdapter = new RVAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(mController.getContext(), 2, LinearLayoutManager.VERTICAL, false));
        if(mRecyclerView.getAdapter()!=mRVAdapter)
            mRecyclerView.setAdapter(mRVAdapter);
        mRVAdapter.onRefreshRequested(new RefreshEvent());
    }
}
