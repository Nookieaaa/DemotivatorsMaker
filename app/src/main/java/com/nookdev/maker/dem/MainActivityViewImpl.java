package com.nookdev.maker.dem;


import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nookdev.maker.dem.fragments.ConstructorFragment;
import com.nookdev.maker.dem.fragments.PreviewFragment;
import com.nookdev.maker.dem.fragments.SavedPicsFragment;
import com.nookdev.maker.dem.interfaces.MainActivityController;
import com.nookdev.maker.dem.interfaces.MainActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivityViewImpl implements MainActivityView {
    private static MainActivityViewImpl mInstance = new MainActivityViewImpl();
    private MainActivityController mController;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.tablayout)
    TabLayout mTabLayout;

    @Bind(R.id.pager)
    ViewPager mViewPager;

    public static MainActivityViewImpl getInstance() {
        return mInstance;
    }

    private MainActivityViewImpl() {
    }

    public void setViewWithController(View v, MainActivityController controller){
        ButterKnife.bind(this,v);
        setController(controller);
    }

    private void setController(MainActivityController mController) {
        this.mController = mController;
    }

    @Override
    public void setupViews() {
        setupActionBar();
        setupViewPager();

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

        adapter.addFragment(new ConstructorFragment(),App.getStringResource(R.string.tab_constructor_title));
        adapter.addFragment(new PreviewFragment(), App.getStringResource(R.string.tab_preview_title));
        adapter.addFragment(new SavedPicsFragment(),App.getStringResource(R.string.tab_collection_title));

        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (mViewPager.getCurrentItem() != 0) {
                        ((InputMethodManager) App.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
                        mFab.hide();
                    }
                    if (mViewPager.getCurrentItem() == 0) {
                        mFab.show();
                    }
                }
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
