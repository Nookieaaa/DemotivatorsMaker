package com.nookdev.maker.dem.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.android.Main
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val scopeJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + scopeJob)

    fun launchOnUiScope(block: suspend () -> Job) = uiScope.launch {
        block()
    }

    override fun onCleared() {
        super.onCleared()
        scopeJob.cancel()
    }
}