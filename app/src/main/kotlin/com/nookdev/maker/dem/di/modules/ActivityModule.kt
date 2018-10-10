package com.nookdev.maker.dem.di.modules

import android.app.Activity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: Activity) {
    @Provides
    fun provideActivity() = activity
}