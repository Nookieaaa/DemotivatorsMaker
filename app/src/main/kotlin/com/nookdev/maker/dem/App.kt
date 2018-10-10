package com.nookdev.maker.dem

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import com.nookdev.maker.dem.di.components.ApplicationComponent
import com.nookdev.maker.dem.di.components.DaggerApplicationComponent
import com.nookdev.maker.dem.di.modules.ContextModule
import timber.log.Timber

class App : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        applicationComponent = DaggerApplicationComponent.builder()
                .contextModule(ContextModule(this))
                .build()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    fun component() = applicationComponent

    companion object {
        operator fun get(activity: Activity?): App {
            return activity?.application as? App ?: throw IllegalStateException()
        }

        operator fun get(service: Service): App {
            return service.application as App
        }

        operator fun get(context: Context?): App {
            return context?.applicationContext as? App ?: wtf("Context is null.")
        }
    }
}