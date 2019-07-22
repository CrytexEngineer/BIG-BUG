package com.example.detailnews.detailpage;

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.detailnews.R
import id.co.gits.gitsutils.extensions.replaceFragmentInActivity


class MainDetailActivity : AppCompatActivity(), MainDetailNavigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_detail)
        setupFragment()
    }

    // TODO add MainDetailViewModel to ViewModelFactory & if template have an error, please reimport obtainViewModel
    fun obtainViewModel(): MainDetailViewModel = ViewModelProviders.of(this)[MainDetailViewModel::class.java]

    private fun setupFragment() {
        supportFragmentManager.findFragmentById(R.id.frame_main_content)
        MainDetailFragment.newInstance().let {
            // TODO if template have an error, please reimport replaceFragmentInActivity
            replaceFragmentInActivity(it, R.id.frame_main_content)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainDetailActivity::class.java))
        }
    }
}
