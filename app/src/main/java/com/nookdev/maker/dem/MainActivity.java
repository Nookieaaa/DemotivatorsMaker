package com.nookdev.maker.dem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.appodeal.ads.BannerView;
import com.nookdev.maker.dem.fragments.ConstructorFragment;
import com.nookdev.maker.dem.fragments.SavedPicsFragment;
import com.nookdev.maker.dem.interfaces.ImagePicker;
import com.nookdev.maker.dem.interfaces.ImageSetter;
import com.nookdev.maker.dem.interfaces.SaveCallback;
import com.nookdev.maker.dem.interfaces.ShareListInterface;
import com.nookdev.maker.dem.models.Demotivator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ImagePicker,ShareListInterface,SaveCallback {

    ShareActionProvider actionProvider;
    List<Uri> shareList = new ArrayList<>();

    public static final int PICK_IMAGE_CODE = 100;
    public static final int TAKE_PICTURE_CODE = 101;

    Uri savedDem;
    ImageSetter fragmentImageSetter;
    DemotivatorSaver mDemotivatorSaver;
    ListUpdater mListUpdater;
    MenuItem shareMenuItem;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.appodealBannerView)
    BannerView bannerView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager viewPager;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof ConstructorFragment) {
            fragmentImageSetter = (ImageSetter) fragment;
            mDemotivatorSaver = (DemotivatorSaver)fragment;
        }
        if(fragment instanceof SavedPicsFragment){
            mListUpdater = (ListUpdater)fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.mipmap.ic_launcher);
        }
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.menu_share);
        shareMenuItem = item;
        shareMenuItem.setVisible(false);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        updateShareIntent();

        return true;
    }

    public void updateShareIntent(){
        if (actionProvider!=null){
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

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void pickImage(int source) {
        switch (source){
            case SOURCE_GALLERY:{
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PICK_IMAGE_CODE);
                break;
            }
            case SOURCE_CAMERA:{
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra( MediaStore.EXTRA_OUTPUT, FileManager.getInstance().getTempFileUri());
                startActivityForResult(intent, TAKE_PICTURE_CODE);
                break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_IMAGE_CODE: {
                try {
                    Uri selectedImage = data.getData();
                    InputStream is = getContentResolver().openInputStream(selectedImage);
                    Bitmap image = Demotivator.scaleImage(BitmapFactory.decodeStream(is));
                    fragmentImageSetter.setImage(image);
                    break;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            case TAKE_PICTURE_CODE: {
                FileManager fm = FileManager.getInstance();
                File output = new File(fm.getTempFileUri().getPath());
                if (output.exists()) {
                    Bitmap image = Demotivator.scaleImage(BitmapFactory.decodeFile(output.getAbsolutePath()));
                    fragmentImageSetter.setImage(image);
                }
            }

        }
    }

    public void setupViewPager(ViewPager upViewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ConstructorFragment(),getString(R.string.tab_constructor_title));
        adapter.addFragment(new SavedPicsFragment(),getString(R.string.tab_collection_title));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE)
                {
                    if (viewPager.getCurrentItem() == 1)
                    {
                        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(viewPager.getWindowToken(), 0);
                        fab.hide();
                        shareMenuItem.setVisible(false);
                    }
                    else if (viewPager.getCurrentItem() == 0){
                        fab.show();
                        if((savedDem!=null))
                            updateShareIntent();
                    }
                }
            }
        });
    }

    @Override
    public void add(Uri uri) {

    }

    @Override
    public void remove(Uri uri) {

    }

    @Override
    public List<Uri> getList() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public void deliverSaveResult(Uri fileUri, boolean success) {
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
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public interface DemotivatorSaver{
        public void save() throws FileManager.ExternalStorageNotReadyException, FileManager.DirectoryCreationFailed;
    }

    public interface ListUpdater{
        public void update();
    }

}
