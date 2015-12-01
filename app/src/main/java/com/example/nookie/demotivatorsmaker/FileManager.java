package com.example.nookie.demotivatorsmaker;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.example.nookie.demotivatorsmaker.models.Demotivator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileManager {
    private static FileManager instance = new FileManager();
    public static final String SAVE_TARGET_SYSTEM_PATH_EXT = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    public static final String SAVE_TARGET_FOLDER_NAME = "my dems";


    private FileManager(){
    }

    private static boolean externalStorageReadable, externalStorageWritable;

    public static boolean isExternalStorageReadable() {
        checkStorage();
        return externalStorageReadable;
    }

    public static boolean isExternalStorageWritable() {
        checkStorage();
        return externalStorageWritable;
    }

    public static boolean isExternalStorageReadableAndWritable() {
        checkStorage();
        return externalStorageReadable && externalStorageWritable;
    }

    private static void checkStorage() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            externalStorageReadable = externalStorageWritable = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED) || state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            externalStorageReadable = true;
            externalStorageWritable = false;
        } else {
            externalStorageReadable = externalStorageWritable = false;
        }
    }

    public static FileManager getInstance(){
        return instance;
    }

    public Uri saveDem(Demotivator demotivator) throws ExternalStorageNotReadyException{

        Bitmap result = demotivator.toBitmap();

        if (!isExternalStorageWritable()){
            throw new ExternalStorageNotReadyException();
        }

        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"testdem.jpg");
        Uri fileUri = Uri.fromFile(f);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            result.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            updateMediaScanner(fileUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileUri;
    }

    public void updateMediaScanner(Uri file){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED,file);
        App.getAppContext().sendBroadcast(scanIntent);
    }

    public class ExternalStorageNotReadyException extends Throwable{


        public ExternalStorageNotReadyException() {
            super(App.getStringResource(R.string.error_storage_not_ready));
        }
    }
}
