package com.nookdev.maker.dem.view.library

import com.nookdev.maker.dem.R
import com.nookdev.maker.dem.view.BaseFragment
import com.nookdev.maker.dem.view.MainActivity

class SavedPicsFragment : BaseFragment() {
    override fun provideLayoutId() = R.layout.fragment_saved_pics

    override fun performInjection() {
        MainActivity[this].inject(this)
    }

}