package com.nookdev.maker.dem.helpers;


import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerView;
import com.nookdev.maker.dem.BuildConfig;

import lombok.Getter;
import lombok.Setter;

public class Ads {
    private static Ads sInstance = new Ads();
    @Getter @Setter private Activity activity;
    @Getter @Setter BannerView bannerView;
    @Getter @Setter boolean showing;
    @Setter int mBannerId;

    private Ads(){

    }

    public static Ads getInstance(){
        return sInstance;
    }

    public static void init(Activity a){
        getInstance().setActivity(a);
        Appodeal.initialize(a, BuildConfig.APPODEAL_API_KEY, Appodeal.BANNER_VIEW);
        Appodeal.disableLocationPermissionCheck();
        Appodeal.setTesting(BuildConfig.DEBUG);
        Appodeal.setLogging(BuildConfig.DEBUG);
        //Appodeal.setBannerViewId(BANNER_ID);
        //show();
    }

    public static void onResume(Activity activity) {
        Appodeal.onResume(activity, Appodeal.BANNER_BOTTOM);
        getInstance().updateVisibility();
    }

    public static void show() {
        if(!getInstance().isShowing()){

            Appodeal.show(getInstance().getActivity(),Appodeal.BANNER_VIEW);
            getInstance().updateVisibility();
        }

    }

    public static void hide(){
        if(getInstance().isShowing()){
            //getInstance().getBannerView().setVisibility(View.GONE);
            Appodeal.hide(getInstance().getActivity(),Appodeal.BANNER_VIEW);
        //Appodeal.getBannerView(getInstance().getActivity()).setVisibility(View.GONE);
            getInstance().updateVisibility();
        }

    }

    private void updateVisibility(){
        //setShowing(getInstance().getBannerView().getVisibility()== View.VISIBLE);
    }

}
