package com.example.nookie.demotivatorsmaker;


import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getAppContext(){
        return context;
    }

    public static String getStringResource(int resId){
        return getAppContext().getString(resId);
    }
}
