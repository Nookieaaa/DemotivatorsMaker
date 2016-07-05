package com.nookdev.maker.dem;


import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import lombok.Getter;

public class App extends MultiDexApplication{
    private static Context context;
    public @Getter static Bus bus = new Bus(ThreadEnforcer.ANY);

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }



    public static Context getAppContext(){
        return context;
    }

    public static DisplayMetrics resolveDisplayMetrics(){
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static String getStringResource(int resId){
        return getAppContext().getString(resId);
    }


}
