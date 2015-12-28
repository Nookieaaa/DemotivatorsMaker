package com.nookdev.maker.dem.helpers;

import android.net.Uri;
import android.os.AsyncTask;

import com.nookdev.maker.dem.models.Demotivator;

public class SaveFileTask extends AsyncTask<Demotivator,Void,Uri> {

    @Override
    protected Uri doInBackground(Demotivator... params) {
        Uri result = null;
        if (params.length==0)
            return result;
        FileManager fileManager = FileManager.getInstance();
        try {
            result = fileManager.saveDem(params[0]);
        } catch (FileManager.ExternalStorageNotReadyException | FileManager.DirectoryCreationFailed e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    protected void onPostExecute(Uri uri) {
        //mSaveCallback.deliverSaveResult(uri,(uri!=null));
    }
}