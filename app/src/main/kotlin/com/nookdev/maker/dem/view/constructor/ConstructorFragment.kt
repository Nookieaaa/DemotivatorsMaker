package com.nookdev.maker.dem.view.constructor

import android.os.Bundle
import android.view.View
import com.nookdev.maker.dem.R
import com.nookdev.maker.dem.view.BaseFragment
import com.nookdev.maker.dem.view.MainActivity

class ConstructorFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun provideLayoutId() = R.layout.fragment_constructor

    override fun performInjection() {
        MainActivity[this].inject(this)
    }

}