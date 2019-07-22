package com.example.gitsnews.mainpage.mvvm

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

/**
 * Created by radhikayusuf on 17/11/2018.
 */

class HomeVMFactory private constructor(
        private val mApplication: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(HomeViewModel::class.java) ->
                        HomeViewModel(mApplication)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @Volatile private var INSTANCE: HomeVMFactory? = null
        fun getInstance(mApplication: Application) =
                INSTANCE ?: synchronized(HomeVMFactory::class.java) {
                    INSTANCE ?: HomeVMFactory(mApplication)
                            .also { INSTANCE = it }
                }
    }
}