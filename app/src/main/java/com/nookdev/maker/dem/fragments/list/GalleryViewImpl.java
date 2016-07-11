package com.nookdev.maker.dem.fragments.list;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.RefreshEvent;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

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
    TextView mEmptyList;

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
        if(mRecyclerView.getContext().getResources().getConfiguration().orientation==ORIENTATION_LANDSCAPE){
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mController.getContext(),LinearLayoutManager.HORIZONTAL, false));
        }else{
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mController.getContext(),LinearLayoutManager.VERTICAL, false));
        }
        if(mRecyclerView.getAdapter()!=mRVAdapter)
            mRecyclerView.setAdapter(mRVAdapter);
        mRVAdapter.onRefreshRequested(new RefreshEvent());
    }
}
