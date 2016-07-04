package com.nookdev.maker.dem.fragments.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.DeleteDemEvent;
import com.nookdev.maker.dem.fragments.DeleteDialog;
import com.squareup.otto.Subscribe;

public class GalleryFragment extends Fragment {

    public static final String TAG_NAME = GalleryFragment.class.getSimpleName();

    private GalleryController mController = GalleryControllerImpl.getInstance();


    public GalleryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_pics, container, false);
        App.getBus().register(mController);
        mController.setView(v);
        mController.setContext(getActivity());
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        App.getBus().unregister(mController);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        App.getBus().unregister(this);
    }

    @Subscribe
    public void onDeleteDemEvent(DeleteDemEvent event){
        DeleteDialog dialog = DeleteDialog.newInstance(event.getFileUri(),event.getPosition());
        dialog.show(getFragmentManager(),TAG_NAME);
    }
}
