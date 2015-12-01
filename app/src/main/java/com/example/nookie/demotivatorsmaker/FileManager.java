package com.example.nookie.demotivatorsmaker;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.nookie.demotivatorsmaker.models.Demotivator;
import com.example.nookie.demotivatorsmaker.models.RVItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static FileManager instance = new FileManager();
    public static final String SAVE_TARGET_SYSTEM_PATH_EXT = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    public static final String SAVE_TARGET_FOLDER_NAME = "my dems";


    private FileManager(){
    }

    private File getFolder(){
        File targetDir = new File(SAVE_TARGET_SYSTEM_PATH_EXT,SAVE_TARGET_FOLDER_NAME);
        return targetDir;
    }


    public List<RVItem> queryFiles(){
        ArrayList<RVItem> data = new ArrayList<>();
        String targetFolder = getFolder().getAbsolutePath();
        ContentResolver cr = App.getAppContext().getContentResolver();

        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri mThumbnailUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;

        String[] imagesProjection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
        String imagesSelection = MediaStore.Images.Media.DATA + " LIKE ? ";
        Cursor images = cr.query(mImageUri,imagesProjection,imagesSelection,new String[]{targetFolder+"%"},null);
        for (images.moveToFirst();!images.isAfterLast();images.moveToNext()){
            RVItem item = new RVItem(Uri.parse(images.getString(images.getColumnIndex(MediaStore.Images.Media.DATA))));
            String imageID = images.getString(images.getColumnIndex(MediaStore.Images.Media._ID));
            String[] thumbnailsProjection = {MediaStore.Images.Thumbnails.DATA};
            String thumbnailsSelection = MediaStore.Images.Thumbnails.IMAGE_ID + " =? ";

            Cursor thumbnails = cr.query(mThumbnailUri,thumbnailsProjection,thumbnailsSelection,new String[]{imageID},null);
            if (thumbnails!=null && thumbnails.moveToFirst()){
                String filepath = images.getString(images.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                item.setThumbnail(Uri.parse(filepath));
            }

            data.add(item);
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

    public Uri saveDem(Demotivator demotivator) throws ExternalStorageNotReadyException, DirectoryCreationFailed {

        Bitmap result = demotivator.toBitmap();

        if (!isExternalStorageWritable()){
            throw new ExternalStorageNotReadyException();
        }

        File targetDir = getFolder();
        createFolderIfNeeded();


        File f = new File(targetDir,"testdem.jpg");
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


    private void createFolderIfNeeded() throws DirectoryCreationFailed {
        File folder = getFolder();
        if (!folder.exists()){
            boolean success = folder.mkdirs();
            if (!success){
                throw new DirectoryCreationFailed();
            }
        }
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

    public class DirectoryCreationFailed extends Throwable{
        public DirectoryCreationFailed() {
            super(App.getStringResource(R.string.error_directory_not_created));
        }
    }
}
