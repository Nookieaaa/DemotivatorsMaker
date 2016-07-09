package com.nookdev.maker.dem.helpers;


import android.app.Activity;

import com.appodeal.ads.Appodeal;
import com.nookdev.maker.dem.BuildConfig;

import lombok.Getter;
import lombok.Setter;

public class Ads {
    private static Ads sInstance = new Ads();
    @Getter @Setter private Activity activity;

    private Ads(){

    }

    public static Ads getInstance(){
        return sInstance;
    }

    public static void init(Activity activity){
        getInstance().setActivity(activity);
        Appodeal.disableLocationPermissionCheck();
        Appodeal.initialize(activity, BuildConfig.APPODEAL_API_KEY,Appodeal.INTERSTITIAL);
        Appodeal.initialize(activity, BuildConfig.APPODEAL_API_KEY,Appodeal.NON_SKIPPABLE_VIDEO);
        Appodeal.setTesting(BuildConfig.DEBUG);
        Appodeal.setLogging(BuildConfig.DEBUG);
    }

    public static void onResume(Activity a){
        Appodeal.onResume(a,Appodeal.INTERSTITIAL);
        Appodeal.onResume(a,Appodeal.NON_SKIPPABLE_VIDEO);
    }

    public static void showtime(){
        Appodeal.show(getInstance().getActivity(),Appodeal.INTERSTITIAL);
    }
}
