package com.example.basemvvm.base.splashscreen

import android.app.Application
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.basemvvm.R
import com.example.basemvvm.base.data.model.Article
import id.co.gits.gitsutils.extensions.navigatorImplicit
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.data.source.local.GitsLocalDataSource
import id.gits.gitsmvvmkotlin.data.source.remote.GitsRemoteDataSource

class SplashscreenActivity : AppCompatActivity() {
    private var localDataSource: GitsDataSource? = null
    private var remoteDataSource: GitsDataSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen_activity)
        Handler().postDelayed({
            navigatorImplicit(this@SplashscreenActivity,
                "com.example.gitsnews.mainpage.mvvm.HomeActivity")
        }, 2000)

        localDataSource = GitsLocalDataSource(applicationContext)
        remoteDataSource = GitsRemoteDataSource


    }
}
