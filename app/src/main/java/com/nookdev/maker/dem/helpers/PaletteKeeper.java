package com.nookdev.maker.dem.helpers;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.graphics.Palette;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PaletteKeeper {
    private static PaletteKeeper mInstance = new PaletteKeeper();
    private Palette mPalette;
    public static final int DEFAULT_COLOR = Color.BLACK;

    private PaletteKeeper(){
    }

    public void generatePalette(Bitmap bitmap){
        Observable.just(Palette.from(bitmap).generate())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(palette -> this.mPalette = palette,
                        Throwable::printStackTrace);
    }

    private Palette getPalette(){
        return mPalette;
    }

}
