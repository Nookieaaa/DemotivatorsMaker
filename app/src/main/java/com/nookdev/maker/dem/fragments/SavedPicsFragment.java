package com.nookdev.maker.dem.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.MainActivity;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.RVAdapter;
import com.nookdev.maker.dem.interfaces.AdapterCallbacks;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedPicsFragment extends Fragment implements MainActivity.ListUpdater,AdapterCallbacks,DeleteDialog.DialogCallbacks {

    public static final String TAG_NAME = SavedPicsFragment.class.getSimpleName();
    public static final String LAST_POS_TAG = "last_position";
    public static final String LAST_URI_TAG = "last_uri";
    private int lastPos;
    private Uri lastUri;


    @Bind(R.id.rv_pics)
    RecyclerView recyclerView;

    public SavedPicsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (recyclerView!=null && recyclerView.getAdapter()!=null)
            requestRefresh();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LAST_URI_TAG, lastUri);
        outState.putInt(LAST_POS_TAG, lastPos);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_pics, container, false);
        ButterKnife.bind(this, v);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RVAdapter(this));

        if (savedInstanceState!=null && savedInstanceState.containsKey(LAST_POS_TAG) &&
                savedInstanceState.containsKey(LAST_URI_TAG)){
                lastUri = savedInstanceState.getParcelable(LAST_URI_TAG);
                lastPos = savedInstanceState.getInt(LAST_POS_TAG);
                updateDialog();
        }

        return v;
    }

    private void updateDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DeleteDialog d = (DeleteDialog)fm.findFragmentByTag(DeleteDialog.TAG);
        if (d!=null && lastUri != null) {
            d.setFileUri(lastUri);
            d.setCallback(this);
            d.setPosition(lastPos);
        }
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
    public void update() {
        requestRefresh();
    }

    @Override
    public void openImage(Uri uri) {
        Intent intent = new Intent();
        intent.setDataAndType(uri, "image/*");
        intent.setAction(Intent.ACTION_VIEW);
        getActivity().startActivity(intent);
    }

    @Override
    public void delete(Uri uri, int position) {
        lastPos = position;
        lastUri = uri;
        DeleteDialog dialog= DeleteDialog.newInstance(this, uri, position);
        dialog.show(getActivity().getSupportFragmentManager(), DeleteDialog.TAG);
    }

    @Override
    public void share(Uri uri) {
        try{
            MainActivity activity = (MainActivity)getActivity();
            Intent shareIntent = new Intent(Intent.ACTION_SEND)
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_STREAM, uri)
                    .setType("image/*");
            activity.startActivity(Intent.createChooser(shareIntent, App.getStringResource(R.string.action_share)));
        }catch (ClassCastException e)
        {
            e.printStackTrace();
        }

    }


    public void requestRefresh(){
        RVAdapter adapter = (RVAdapter)recyclerView.getAdapter();
        adapter.refresh();
    }

    @Override
    public void deliverResult(boolean result, int position) {
        RVAdapter adapter = (RVAdapter)recyclerView.getAdapter();
        adapter.itemDeleted(position);
    }
}
