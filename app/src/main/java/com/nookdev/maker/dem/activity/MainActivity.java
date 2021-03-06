package com.nookdev.maker.dem.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.CheckPermissionAndExecuteEvent;
import com.nookdev.maker.dem.events.RefreshEvent;
import com.nookdev.maker.dem.events.SaveDemEvent;
import com.nookdev.maker.dem.helpers.Ads;
import com.nookdev.maker.dem.helpers.FileManager;
import com.squareup.otto.Subscribe;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static com.nookdev.maker.dem.helpers.Constants.ACTION_TAKE_PHOTO;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    public static final String TAG_NAME = MainActivity.class.getSimpleName();
    private MainActivityController mController;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = MainActivityControllerImpl.getInstance();
        mController.setActivity(this);
        App.getBus().register(this);
        Ads.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Ads.onResume(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mController.deliverImage(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Subscribe
    public void onCheckPermissionAndExecute(CheckPermissionAndExecuteEvent event){
        if(event.getAction()==CheckPermissionAndExecuteEvent.ACTION_SAVE) {
            SaveDemEvent saveDemEvent = new SaveDemEvent();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                saveDem(saveDemEvent);
            else
                MainActivityPermissionsDispatcher.saveDemWithCheck(this, saveDemEvent);
        }else if (event.getAction()==CheckPermissionAndExecuteEvent.ACTION_GALLERY){
            RefreshEvent refreshEvent = new RefreshEvent();
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                App.getBus().post(refreshEvent);
            else
                MainActivityPermissionsDispatcher.refreshGalleryWithCheck(this, refreshEvent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getBus().unregister(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void takeAPhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra( MediaStore.EXTRA_OUTPUT, FileManager.getInstance().getTempFileUri());
        startActivityForResult(intent, ACTION_TAKE_PHOTO);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void saveDem(SaveDemEvent event){
        event.setAllowed(true);
        App.getBus().post(event);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void refreshGallery(RefreshEvent event){
        App.getBus().post(event);
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onPermissionDenied(){
        showMessage(R.string.permission_denied);
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onNeverAsk(){
        showMessage(R.string.on_never_ask);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onShowRationale(PermissionRequest request){
        showMessage(R.string.message_rationale);
        request.proceed();
    }

    private void showMessage(int resId){
        Toast.makeText(this, resId,Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String text){
        Toast.makeText(this, text,Toast.LENGTH_SHORT).show();
    }

}
