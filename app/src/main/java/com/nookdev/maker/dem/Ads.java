package com.nookdev.maker.dem;


import android.app.Activity;
import android.view.View;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.BannerView;

import lombok.Getter;
import lombok.Setter;

public class Ads {
    private static Ads sInstance = new Ads();
    @Getter @Setter private Activity activity;
    @Getter @Setter BannerView bannerView;
    @Getter @Setter boolean showing;
    public static final int BANNER_ID = R.id.banner;

    private Ads(){

    }

    public static Ads getInstance(){
        return sInstance;
    }

    public static void init(Activity a){
        getInstance().setActivity(a);
        getInstance().setBannerView((BannerView)a.findViewById(BANNER_ID));
        getInstance().updateVisibility();
        Appodeal.initialize(a, BuildConfig.APPODEAL_API_KEY, Appodeal.BANNER_VIEW);
        Appodeal.disableLocationPermissionCheck();
        Appodeal.setTesting(BuildConfig.DEBUG);
        Appodeal.setLogging(BuildConfig.DEBUG);
        Appodeal.setBannerViewId(BANNER_ID);
        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int i, boolean b) {
            }

            @Override
            public void onBannerFailedToLoad() {

            }

            @Override
            public void onBannerShown() {
                show();
            }

            @Override
            public void onBannerClicked() {

            }
        });
    }

    public static void onResume(Activity activity) {
        Appodeal.onResume(activity, Appodeal.BANNER_VIEW);
    }

    public static void show() {
        if(!getInstance().isShowing()){
            getInstance().getBannerView().setVisibility(View.VISIBLE);
            Appodeal.show(getInstance().getActivity(),Appodeal.BANNER_VIEW);
            getInstance().updateVisibility();
        }

    }

    public static void hide(){
        if(getInstance().isShowing()){
            getInstance().getBannerView().setVisibility(View.GONE);
            Appodeal.hide(getInstance().getActivity(),Appodeal.BANNER_VIEW);
            getInstance().updateVisibility();
        }

    }

    private void updateVisibility(){
        setShowing(getInstance().getBannerView().getVisibility()==View.VISIBLE);
    }

}
