package com.nookdev.maker.dem.fragments.donate;


import android.view.View;

import com.appodeal.ads.Appodeal;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.helpers.Ads;

import lombok.Getter;

public class DonateController {
    @Getter private DonateView view;

    public void setView(View v) {
        this.view = new DonateView(this,v);
    }

    public void onButtonClick(int id){
        switch (id){
            case R.id.btn_video:{
                Ads.show(Appodeal.NON_SKIPPABLE_VIDEO);
                break;
            }
            case R.id.btn_intertestial:{
                Ads.show(Appodeal.INTERSTITIAL);
                break;
            }
        }
    }

    public void onAdTypeChanged(int adType){
        Ads.setAdType(adType);
    }
}
