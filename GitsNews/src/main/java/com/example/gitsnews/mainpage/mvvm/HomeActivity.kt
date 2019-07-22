package com.example.gitsnews.mainpage.mvvm;

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.gitsnews.R
import com.example.gitsnews.databinding.ActivityHomeBinding
import id.gits.gitsmvvmkotlin.base.BaseActivity


class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    override fun bindLayoutRes(): Int = R.layout.activity_home

    override fun bindToolbarId(): Int = 0

    override fun bindFragmentContainerId(): Int = R.id.frame_container

    override fun bindFragmentInstance(): Fragment = HomeFragment.newInstance()

    override fun onStartWork() {

    }

}

