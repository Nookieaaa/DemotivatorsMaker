package com.example.nookie.demotivatorsmaker.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nookie.demotivatorsmaker.MainActivity;
import com.example.nookie.demotivatorsmaker.R;
import com.example.nookie.demotivatorsmaker.RVAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedPicsFragment extends Fragment implements MainActivity.ListUpdater {

    public static final String TAG_NAME = SavedPicsFragment.class.getSimpleName();


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
    public void update() {
        requestRefresh();
    }

    //TODO finish dialog
    public static class FilenameDialog extends AppCompatDialogFragment{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.dialog_filename_title)
                    .setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //TODO check if file exists

                        }
                    })
                    .setCancelable(true);
            return builder.create();
        }
    }

    public void requestRefresh(){
        RVAdapter adapter = (RVAdapter)recyclerView.getAdapter();
        adapter.refresh();
    }

}
