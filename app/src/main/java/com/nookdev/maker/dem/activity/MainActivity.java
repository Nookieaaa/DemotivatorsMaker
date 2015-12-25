package com.nookdev.maker.dem.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.nookdev.maker.dem.helpers.FileManager;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.interfaces.ImagePicker;
import com.nookdev.maker.dem.interfaces.ImageSetter;
import com.nookdev.maker.dem.interfaces.SaveCallback;

public class MainActivity extends AppCompatActivity implements ImagePicker,SaveCallback {

    MainActivityController mController;

    ShareActionProvider actionProvider;

    Uri savedDem;
    ImageSetter fragmentImageSetter;
    DemotivatorSaver mDemotivatorSaver;
    ListUpdater mListUpdater;
    MenuItem shareMenuItem;


    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        /*if(fragment instanceof ConstructorFragment) {
            fragmentImageSetter = (ImageSetter) fragment;
            mDemotivatorSaver = (DemotivatorSaver)fragment;
        }
        if(fragment instanceof SavedPicsFragment){
            mListUpdater = (ListUpdater)fragment;
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = MainActivityControllerImpl.getInstance();
        mController.setActivity(this);
    /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mDemotivatorSaver.save();
                } catch (FileManager.ExternalStorageNotReadyException | FileManager.DirectoryCreationFailed e) {
                    e.printStackTrace();
                }
            }
        });

*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        /*MenuItem item = menu.findItem(R.id.menu_share);
        shareMenuItem = item;
        shareMenuItem.setVisible(false);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        updateShareIntent();*/

        return true;
    }

    public void updateShareIntent(){
       /* if (actionProvider!=null){
            if (savedDem!=null){
                shareMenuItem.setVisible(true);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, savedDem);
                shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.share_message));
                shareIntent.setType("image/*");
                actionProvider.setShareIntent(shareIntent);
            }

        }*/
    }

    @Override
    public void pickImage(int source) {
       mController.requestImage(source);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mController.deliverImage(requestCode,resultCode,data);

    }


    @Override
    public void deliverSaveResult(Uri fileUri, boolean success) {
        /*
        if (success && fileUri != null){
            savedDem = fileUri;
            Snackbar.make(viewPager, getString(R.string.status_saved), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.action_open), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(savedDem, "image/*");
                        startActivity(intent);
                    }
                }).show();
            mListUpdater.update();
            updateShareIntent();
        }
        else{
            Snackbar.make(viewPager, getString(R.string.error_file_not_saved), Snackbar.LENGTH_LONG).show();
        }*/
    }


    public interface DemotivatorSaver{
        public void save() throws FileManager.ExternalStorageNotReadyException, FileManager.DirectoryCreationFailed;
    }

    public interface ListUpdater{
        public void update();
    }

}
