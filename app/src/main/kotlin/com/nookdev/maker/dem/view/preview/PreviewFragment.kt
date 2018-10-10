package com.nookdev.maker.dem.view.preview

import com.nookdev.maker.dem.R
import com.nookdev.maker.dem.view.BaseFragment
import com.nookdev.maker.dem.view.MainActivity

class PreviewFragment : BaseFragment() {
    override fun provideLayoutId() = R.layout.fragment_pic_preview

    override fun performInjection() {
        MainActivity[this].inject(this)
    }

}