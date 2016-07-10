package com.nookdev.maker.dem.fragments.donate;


import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.appodeal.ads.Appodeal;
import com.nookdev.maker.dem.R;
import com.nookdev.maker.dem.helpers.Ads;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonateView {
    private DonateController mDonateController;

    @Bind(R.id.ads_rb_video)
    RadioButton mRbVideo;

    @Bind(R.id.ads_rb_fullscreen)
    RadioButton mRbFullscreen;

    @Bind(R.id.radioGroup)
    RadioGroup mRadioGroup;

    @OnClick({R.id.btn_video,R.id.btn_intertestial})
    public void onClick(View v){
        mDonateController.onButtonClick(v.getId());
    }

    public DonateView(DonateController donateController,View v) {
        mDonateController = donateController;
        ButterKnife.bind(this,v);

        int adType = Ads.getAdType();
        if(adType == Appodeal.INTERSTITIAL){
            mRbFullscreen.setChecked(true);
        }else if(adType == Appodeal.NON_SKIPPABLE_VIDEO){
            mRbVideo.setChecked(true);
        }

        if(mRadioGroup!=null){
            mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if(mDonateController==null) {
                    return;
                }
                if (checkedId==R.id.ads_rb_fullscreen){
                    mDonateController.onAdTypeChanged(Appodeal.INTERSTITIAL);
                }else if(checkedId==R.id.ads_rb_video){
                    mDonateController.onAdTypeChanged(Appodeal.NON_SKIPPABLE_VIDEO);
                }
            });
        }
    }

}
