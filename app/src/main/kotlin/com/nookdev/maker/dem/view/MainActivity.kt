package com.nookdev.maker.dem.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.nookdev.maker.dem.App
import com.nookdev.maker.dem.R
import com.nookdev.maker.dem.di.components.ActivityComponent
import com.nookdev.maker.dem.di.components.DaggerActivityComponent
import com.nookdev.maker.dem.di.modules.ActivityModule

class MainActivity : AppCompatActivity() {

    private lateinit var component: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = DaggerActivityComponent.builder()
                .applicationComponent(App[this].component())
                .activityModule(ActivityModule(this))
                .build()

        val finalHost = NavHostFragment.create(R.navigation.main_navigation)
        supportFragmentManager.beginTransaction()
                .replace(R.id.mainNavHostContainer, finalHost)
                .setPrimaryNavigationFragment(finalHost)
                .commit()
    }

    companion object {
        operator fun get(fragment: Fragment): ActivityComponent {
            return (fragment.activity as MainActivity).component
        }
    }
}