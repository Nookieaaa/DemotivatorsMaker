package com.nookdev.maker.dem.helpers;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.appodeal.ads.Appodeal;
import com.nookdev.maker.dem.BuildConfig;

import java.util.Calendar;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

public class Ads {
    public static final String TAG = "ADS";
    public static final String AD_PREFERENCES = "ads";
    public static final String AD_TYPE = "ad_type";
    public static final String DEM_COUNT = "dem_count";
    public static final String VIDEO_PLAYED_DATE = "video_date";
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

    public static void show(int adType) {
        if(Appodeal.isLoaded(adType)){
            Appodeal.show(getInstance().getActivity(),adType);
        }
    }

    public static void onDemSaved(){
        Ads ads = getInstance();
        if(ads.getAdType()==Appodeal.NON_SKIPPABLE_VIDEO){
            if(!ads.isVideoShownToday()){
                Log.d(TAG, "isvideoShown: false");
                show(Appodeal.NON_SKIPPABLE_VIDEO);
                ads.saveVideoShown();
            }else{
                Log.d(TAG, "isvideoShown: true");
            }
        }else if(ads.getAdType()==Appodeal.INTERSTITIAL){
            int demCount = ads.getDemCount();{
            if(demCount%2==0){
               show(Appodeal.INTERSTITIAL);
            }
            Ads.incrementDemCount();
            }
        }
    }


    private static void incrementDemCount() {
        SharedPreferences.Editor ed = getInstance().getAdPreferences().edit();
        int demCount = getInstance().getDemCount()+1;
        ed.putInt(DEM_COUNT,demCount);
        ed.apply();
        Log.d(TAG, "incrementDemCount: "+String.valueOf(demCount));
    }

    private SharedPreferences getAdPreferences(){
        return getActivity().getSharedPreferences(AD_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void setAdType(int adType){
        SharedPreferences sp = getInstance().getAdPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(AD_TYPE,adType);
        editor.apply();
    }
    public static int getAdType(){
        return getInstance().getAdPreferences().getInt(AD_TYPE,Appodeal.INTERSTITIAL);
    }

    private int getDemCount(){
        return getAdPreferences().getInt(DEM_COUNT,1);
    }

    private boolean isVideoShownToday(){
        long playDate = getAdPreferences().getLong(VIDEO_PLAYED_DATE,0);
        long currentTime = System.currentTimeMillis();
        if(playDate!=0){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getDefault());
            calendar.setTimeInMillis(currentTime);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if(calendar.getTimeInMillis()>playDate){
                return false;
            }
            else{
                return true;
            }
        }else {
            return false;
        }
    }

    private void saveVideoShown(){
        SharedPreferences.Editor ed = getAdPreferences().edit();
        ed.putLong(VIDEO_PLAYED_DATE,System.currentTimeMillis());
        ed.apply();
    }
}
