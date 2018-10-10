package com.nookdev.maker.dem.di.components

import android.content.Context
import com.nookdev.maker.dem.di.modules.ContextModule
import com.nookdev.maker.dem.di.scopes.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [ContextModule::class])
interface ApplicationComponent {
    val context: Context
}