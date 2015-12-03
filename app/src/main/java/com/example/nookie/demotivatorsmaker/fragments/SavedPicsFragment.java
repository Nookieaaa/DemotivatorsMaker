package com.example.nookie.demotivatorsmaker.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nookie.demotivatorsmaker.App;
import com.example.nookie.demotivatorsmaker.FileManager;
import com.example.nookie.demotivatorsmaker.MainActivity;
import com.example.nookie.demotivatorsmaker.R;
import com.example.nookie.demotivatorsmaker.RVAdapter;
import com.example.nookie.demotivatorsmaker.interfaces.AdapterCallbacks;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedPicsFragment extends Fragment implements MainActivity.ListUpdater,AdapterCallbacks {

    public static final String TAG_NAME = SavedPicsFragment.class.getSimpleName();


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_pics, container, false);
        ButterKnife.bind(this,v);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RVAdapter(this));

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
    public void update() {
        requestRefresh();
    }

    @Override
    public void openImage(Uri uri) {
        Intent intent = new Intent();
        intent.setDataAndType(uri,"image/*");
        intent.setAction(Intent.ACTION_VIEW);
        getActivity().startActivity(intent);
    }

    @Override
    public void delete(Uri uri) {
        FileManager fm = FileManager.getInstance();
        fm.delete(uri);
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

}
