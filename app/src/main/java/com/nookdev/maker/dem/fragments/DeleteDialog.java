package com.nookdev.maker.dem.fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.R;

public class DeleteDialog extends AppCompatDialogFragment {
    public static final String TAG = "DeleteDialog";
    Uri fileUri;
    DialogCallbacks callback;
    int position;

    public static DeleteDialog newInstance(DialogCallbacks callback,Uri file, int position){
        DeleteDialog dialog = new DeleteDialog();
        dialog.setCallback(callback);
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

    public void setCallback(DialogCallbacks callback) {
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.dialog_delete_title)
                .setMessage(R.string.dialog_delete_confirm_message)
                .setIcon(android.R.drawable.ic_delete)
                .setCancelable(true)
                .setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteTask deleteTask = new DeleteTask();
                        deleteTask.execute(fileUri);
                        callback.deliverResult(true, position);
                    }
                })
                .setNegativeButton(R.string.action_cancel,null);


        return adb.create();
    }

    private class DeleteTask extends AsyncTask<Uri,Void,Void>{

        @Override
        protected Void doInBackground(Uri... params) {
            if (params.length>0){
                Uri fileUri = params[0];
                FileManager fm = FileManager.getInstance();
                fm.delete(fileUri);
            }
            return null;
        }
    }

    public interface DialogCallbacks{
        public void deliverResult(boolean result, int position);
    }

}
