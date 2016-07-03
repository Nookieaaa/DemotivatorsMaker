package com.nookdev.maker.dem.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.CheckPermissionAndExecuteEvent;
import com.nookdev.maker.dem.events.DeliverImageEvent;
import com.nookdev.maker.dem.events.DemSavedEvent;
import com.nookdev.maker.dem.events.ImagePickEvent;
import com.nookdev.maker.dem.events.ShareOpenEvent;
import com.nookdev.maker.dem.helpers.Constants;
import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.models.Demotivator;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.nookdev.maker.dem.helpers.Constants.ACTION_PICK_IMAGE;
import static com.nookdev.maker.dem.helpers.Constants.ACTION_TAKE_PHOTO;


public class MainActivityControllerImpl implements MainActivityController {
    private static MainActivityControllerImpl instance = new MainActivityControllerImpl();
    private MainActivityView mMainActivityView;
    private MainActivity mMainActivity;

    private MainActivityControllerImpl(){
        App.getBus().register(this);
    }

    public static MainActivityControllerImpl getInstance(){
        return instance;
    }

    @Subscribe
    public void requestImage(ImagePickEvent event) {
        switch (event.getRequestCode()){
            case ACTION_PICK_IMAGE:{
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                mMainActivity.startActivityForResult(intent, ACTION_PICK_IMAGE);
                break;
            }
            case ACTION_TAKE_PHOTO:{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra( MediaStore.EXTRA_OUTPUT, FileManager.getInstance().getTempFileUri());
                mMainActivity.startActivityForResult(intent, ACTION_TAKE_PHOTO);
                break;
            }
        }
    }

    @Subscribe
    public void onDemSaved(DemSavedEvent event){
        mMainActivityView.notifySaveResult(event);
        //App.getBus().post(new RefreshEvent());
    }

    @Override
    public void deliverImage(int requestCode, int resultCode,Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        switch (requestCode) {
            case ACTION_PICK_IMAGE: {
                try {
                    Uri selectedImage = data.getData();
                    InputStream is = mMainActivity.getContentResolver().openInputStream(selectedImage);
                    Bitmap image = Demotivator.scaleImage(BitmapFactory.decodeStream(is));
                    App.getBus().post(new DeliverImageEvent(image));
                    break;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            case ACTION_TAKE_PHOTO: {
                FileManager fm = FileManager.getInstance();
                File output = new File(fm.getTempFileUri().getPath());
                if (output.exists()) {
                    Bitmap image = Demotivator.scaleImage(BitmapFactory.decodeFile(output.getAbsolutePath()));
                    App.getBus().post(new DeliverImageEvent(image));
                }
            }
        }
    }

    @Subscribe
    public void onShareOpenEvent(ShareOpenEvent event){
        Uri uri = event.getUri();
        if(uri==null){
            Log.d("DemotivateMe", "file uri is empty ");
            return;
        }
        File f = new File(uri.getPath());
        if(!f.exists())
            return;
        Uri shareUri = FileProvider.getUriForFile(mMainActivity, Constants.FILE_PROVIDER_AUTHORITY,f);

        if(event.isShare()){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM,shareUri);
            intent.putExtra(Intent.EXTRA_TEXT,mMainActivity.getString(R.string.share_message));
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fireIntent(intent,event.isShare());
        }
        else{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(shareUri,"image/*");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fireIntent(intent, event.isShare());
        }

    }

    private void fireIntent(Intent intent,boolean isShare){
        PackageManager pm = mMainActivity.getPackageManager();
        if(pm.resolveActivity(intent,0)!=null)
            if(isShare)
                mMainActivity.startActivity(Intent.createChooser(intent,"share by:"));
            else
                mMainActivity.startActivity(intent);
        else
            Toast.makeText(mMainActivity, R.string.no_app_resolved,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setActivity(MainActivity activity) {
        this.mMainActivity = activity;
        View v = mMainActivity.findViewById(R.id.main_activity_root);
        setView(v);
    }

    @Override
    public MainActivity getActivity() {
        return mMainActivity;
    }

    @Override
    public void setView(View view) {
        mMainActivityView = MainActivityViewImpl.getInstance();
        mMainActivityView.setViewWithController(view, this);
        mMainActivityView.setupViews();
    }

    @Override
    public void fabClicked(int page) {
        switch (page){
            case 0:{
                mMainActivityView.selectFragment(1);
                break;
            }
            case 1:{
                App.getBus().post(new CheckPermissionAndExecuteEvent());
                break;
            }
        }
    }
}
