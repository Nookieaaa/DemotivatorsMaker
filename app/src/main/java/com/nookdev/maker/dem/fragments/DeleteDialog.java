package com.nookdev.maker.dem.fragments;


import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.DemDeletedEvent;
import com.nookdev.maker.dem.helpers.FileManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DeleteDialog extends AppCompatDialogFragment {
    public static final String TAG = "DeleteDialog";
    public static final String KEY_FILE_URI = "FILE_URI";
    public static final String KEY_POSITION = "POSITION";
    Uri fileUri;
    int position;

    public static DeleteDialog newInstance(Uri file, int position){
        DeleteDialog dialog = new DeleteDialog();
        dialog.setFileUri(file);
        dialog.setPosition(position);

        return dialog;
    }

    public void setFileUri(Uri uri) {
        this.fileUri = uri;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(savedInstanceState!=null&&savedInstanceState.containsKey(KEY_FILE_URI)){
            fileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
        }
        if(savedInstanceState!=null&&savedInstanceState.containsKey(KEY_POSITION)){
            position = savedInstanceState.getInt(KEY_POSITION);
        }
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_confirm_message)
                .setIcon(android.R.drawable.ic_delete)
                .setCancelable(true)
                .setPositiveButton(R.string.action_ok, (dialog, which) -> {
                    Observable.just(fileUri)
                            .subscribeOn(Schedulers.io())
                            .map(uri -> {
                                FileManager fm = FileManager.getInstance();
                                return fm.delete(fileUri);
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> {
                                DemDeletedEvent event = new DemDeletedEvent(result,position);
                                App.getBus().post(event);
                            },
                                    Throwable::printStackTrace);
                })
                .setNegativeButton(R.string.action_cancel,null);


        return adb.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION,position);
        outState.putParcelable(KEY_FILE_URI,fileUri);
    }
}
