package com.nookdev.maker.dem.di.components

import com.nookdev.maker.dem.di.modules.ActivityModule
import com.nookdev.maker.dem.di.scopes.ActivityScope
import com.nookdev.maker.dem.view.constructor.ConstructorFragment
import com.nookdev.maker.dem.view.library.SavedPicsFragment
import com.nookdev.maker.dem.view.preview.PreviewFragment
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(fragment: ConstructorFragment)
    fun inject(fragment: SavedPicsFragment)
    fun inject(fragment: PreviewFragment)
}