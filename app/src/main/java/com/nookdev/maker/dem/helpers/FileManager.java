package com.nookdev.maker.dem.helpers;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.DemSavedEvent;
import com.nookdev.maker.dem.events.RefreshEvent;
import com.nookdev.maker.dem.models.Demotivator;
import com.nookdev.maker.dem.models.RVItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FileManager {
    private static final String TMP_NAME = ".tmp";
    private static FileManager instance = new FileManager();
    public static final String SAVE_TARGET_SYSTEM_PATH_EXT = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    public static final String SAVE_TARGET_FOLDER_NAME = "my dems";
    public static final String PREFERENCE_NAME = App.getAppContext().getPackageName()+"_pref";
    public static final String PREFERENCE_IMAGE_ID = "ID";
    public static final String DEFAULT_FILENAME_PREFIX = "mydem_";
    public static final String DEFAULT_EXT = ".jpg";


    private FileManager(){
        try {
        createFolderIfNeeded();
            } catch (DirectoryCreationFailed directoryCreationFailed) {
                directoryCreationFailed.printStackTrace();
            }
    }

    private File getFolder(){
        return new File(SAVE_TARGET_SYSTEM_PATH_EXT,SAVE_TARGET_FOLDER_NAME);
    }


    public List<RVItem> queryFiles(){
        ArrayList<RVItem> data = new ArrayList<>();

        if (isExternalStorageReadable()){
            try {
                createFolderIfNeeded();
            } catch (DirectoryCreationFailed directoryCreationFailed) {
                directoryCreationFailed.printStackTrace();
                return data;
            }
        }

        File targetFile = new File(getFolder().getAbsolutePath());
        File[] files = targetFile.listFiles((dir, filename) -> {
            return filename.endsWith(".jpg");
        });
        if(files!=null) {
            Arrays.sort(files, (f1, f2) -> Long.valueOf(f2.lastModified()).compareTo(f1.lastModified()));
            for (File file : files) {
                RVItem item = new RVItem(Uri.fromFile(file));
                data.add(item);
            }
        }

        return data;
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

    public boolean delete(Uri uri){
        boolean result = false;
        if (isExternalStorageReadableAndWritable()){
            File deletedFile = new File(uri.getPath());
            if (deletedFile.exists()){
                deletedFile.delete();
            }
            updateMediaScanner(Uri.fromFile(deletedFile));
        }

        App.getBus().post(new RefreshEvent());

        return true;
    }

    public Uri saveDem(Demotivator demotivator) throws ExternalStorageNotReadyException, DirectoryCreationFailed {

        Bitmap result = demotivator.toBitmap();

        if (!isExternalStorageWritable()){
            throw new ExternalStorageNotReadyException();
        }

        File targetDir = getFolder();
        createFolderIfNeeded();

        File f=new File("tmp");
        boolean nameAvailable=false;
        while (!nameAvailable) {
            String filename = generateFilename();
            f = new File(targetDir, filename);
            nameAvailable = !f.exists();
        }

        Uri fileUri = Uri.fromFile(f);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            result.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            updateMediaScanner(fileUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileUri;
    }

    private String generateFilename() {
        Context context = App.getAppContext();
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        int id = preferences.getInt(PREFERENCE_IMAGE_ID, 0);

        SharedPreferences.Editor ed = preferences.edit();
        ed.putInt(PREFERENCE_IMAGE_ID, ++id);
        ed.apply();

        return DEFAULT_FILENAME_PREFIX + String.valueOf(id) + DEFAULT_EXT;
    }


    private void createFolderIfNeeded() throws DirectoryCreationFailed {
        File folder = getFolder();
        if (!folder.exists()){
            boolean success = folder.mkdirs();
            if (!success){
                throw new DirectoryCreationFailed();
            }
        }
    }

    private void updateMediaScanner(Uri file){
        MediaScannerConnection.scanFile(App.getAppContext(),
                new String[]{file.getPath()},
                new String[]{"image/*"}
                , (path, uri) -> Log.d("MediaScanner", "scan completed"));
        //Intent scanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED,file);
        //App.getAppContext().sendBroadcast(scanIntent);
    }

    public Uri getTempFileUri() {
        if (isExternalStorageReadableAndWritable()) {
            File tempFile = new File(getFolder(), TMP_NAME);
            return Uri.fromFile(tempFile);
        }
        return null;
    }

    public void saveDem(String caption, String text, Bitmap image) {
        Observable.just(new Demotivator(image,caption,text))
                .map(Demotivator::toBitmap)
                .map(bitmap -> {
                    if (!isExternalStorageWritable()) {
                        try {
                            throw new ExternalStorageNotReadyException();
                        } catch (ExternalStorageNotReadyException e) {
                            e.printStackTrace();
                        }
                    }
                    File targetDir = getFolder();
                    try {
                        createFolderIfNeeded();
                    } catch (DirectoryCreationFailed directoryCreationFailed) {
                        directoryCreationFailed.printStackTrace();
                    }
                    File f = new File("tmp");
                    boolean nameAvailable = false;
                    while (!nameAvailable) {
                        String filename = generateFilename();
                        f = new File(targetDir, filename);
                        nameAvailable = !f.exists();
                    }
                    Uri fileUri = Uri.fromFile(f);
                    try {
                        FileOutputStream fos = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                        updateMediaScanner(fileUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return fileUri;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> deliverSaveResult(uri,true,null),
                        error -> {
                            deliverSaveResult(null,false,error);
                            error.printStackTrace();
                        });

    }

    private void deliverSaveResult(Uri fileUri, boolean success, Throwable error){
        App.getBus().post(new DemSavedEvent(fileUri,error,success));
    }

    public class ExternalStorageNotReadyException extends Throwable{
        public ExternalStorageNotReadyException() {
            super(App.getStringResource(R.string.error_storage_not_ready));
        }
    }

    public class DirectoryCreationFailed extends Throwable{
        public DirectoryCreationFailed() {
            super(App.getStringResource(R.string.error_directory_not_created));
        }
    }



    private int dpToPx(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
