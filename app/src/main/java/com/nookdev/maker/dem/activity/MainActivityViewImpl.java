package com.nookdev.maker.dem.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nookdev.maker.dem.App;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.events.DemSavedEvent;
import com.nookdev.maker.dem.events.RefreshEvent;
import com.nookdev.maker.dem.events.RequestDemInfo;
import com.nookdev.maker.dem.fragments.constructor.ConstructorFragment;
import com.nookdev.maker.dem.fragments.constructor.ConstructorViewImpl;
import com.nookdev.maker.dem.fragments.list.GalleryFragment;
import com.nookdev.maker.dem.fragments.preview.PreviewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityViewImpl implements MainActivityView {
    private static MainActivityViewImpl mInstance = new MainActivityViewImpl();
    private MainActivityController mController;
    private int[] mFabIcons = {R.drawable.ic_arrow_right,android.R.drawable.ic_menu_save};

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.tablayout)
    TabLayout mTabLayout;

    @Bind(R.id.pager)
    ViewPager mViewPager;

    @OnClick(R.id.fab)
    public void fabOnClick(){
        if (mController!=null)
            mController.fabClicked(mViewPager.getCurrentItem());
    }

    public static MainActivityViewImpl getInstance() {
        return mInstance;
    }

    private MainActivityViewImpl() {
    }

    public void setViewWithController(View v, MainActivityController controller){
        ButterKnife.bind(this,v);
        setController(controller);
    }

    @Override
    public void notifySaveResult(DemSavedEvent event) {
        if (event.isSuccess()){
            Snackbar.make(mCoordinator,R.string.status_saved,Snackbar.LENGTH_LONG)
                    .setAction(R.string.action_open, v -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(event.getFileUri(),"image/jpg");
                        mController.getActivity().startActivity(intent);
                    })
                    .show();
        }
        else{
            Snackbar.make(mCoordinator,R.string.error_file_not_saved,Snackbar.LENGTH_LONG).show();
        }
    }

    private void setController(MainActivityController mController) {
        this.mController = mController;
    }

    @Override
    public void setupViews() {
        setupActionBar();
        setupViewPager();

    }

    @Override
    public void selectFragment(int page) {
        mViewPager.setCurrentItem(page);
    }

    private void changeFabIcon(int page){
        Bitmap fabBitmap = BitmapFactory.decodeResource(App.getAppContext().getResources(),
                mFabIcons[page]);
        mFab.setImageBitmap(fabBitmap);
    }

    private void setupActionBar() {
        mController.getActivity().setSupportActionBar(mToolbar);
        ActionBar ab = mController.getActivity().getSupportActionBar();
        if (ab != null) {
            ab.setIcon(R.mipmap.ic_launcher);
        }
    }

    private void setupViewPager() {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(mController.getActivity().getSupportFragmentManager());

        adapter.addFragment(new ConstructorFragment(), App.getStringResource(R.string.tab_constructor_title));
        adapter.addFragment(new PreviewFragment(), App.getStringResource(R.string.tab_preview_title));
        adapter.addFragment(new GalleryFragment(), App.getStringResource(R.string.tab_collection_title));

        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position<=1)
                    changeFabIcon(position);
                if (position==2)
                    mFab.hide();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                int currPage = mViewPager.getCurrentItem();
                if (state == ViewPager.SCROLL_STATE_IDLE) {

                    if (currPage != 0) {
                        ((InputMethodManager) App.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
                    }
                    if (currPage == 1) {
                        requestPreview();
                    }
                    if (currPage == 2) {
                        App.getBus().post(new RefreshEvent());
                        mFab.hide();
                    }
                    else if(currPage==0){
                        if(ConstructorViewImpl.IsFabVisible()){
                            if(!mFab.isShown())
                                mFab.show();
                        }
                        else {
                            mFab.hide();
                        }
                    } else if (mFab.getVisibility() != View.VISIBLE)
                        mFab.show();
                }
            }
            private void requestPreview(){
                App.getBus().post(new RequestDemInfo());
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

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
}
