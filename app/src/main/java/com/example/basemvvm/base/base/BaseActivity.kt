package id.gits.gitsmvvmkotlin.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import id.co.gits.gitsutils.extensions.replaceFragmentInActivity


/**
 * Created by irfanirawansukirman on 26/01/18.
 */

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    lateinit var mActivity: AppCompatActivity
    lateinit var viewBinding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, bindLayoutRes())
        viewBinding.apply {
            mActivity = this@BaseActivity

            setupToolbar()
            replaceFragment()
            onStartWork()
        }
    }

    private fun setupToolbar() {
        if (bindToolbarId() != 0) {
            setSupportActionBar(findViewById(bindToolbarId()))
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
            }
        }
    }

    private fun replaceFragment() {
        replaceFragmentInActivity(bindFragmentInstance(), bindFragmentContainerId())
    }

    @LayoutRes
    abstract fun bindLayoutRes(): Int

    @IdRes
    abstract fun bindToolbarId(): Int

    @IdRes
    abstract fun bindFragmentContainerId(): Int

    abstract fun bindFragmentInstance(): Fragment
    abstract fun onStartWork()

}