package id.co.gits.gitsbase

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.util.Log
import id.co.gits.gitsutils.SingleLiveEvent
import id.gits.gitsmvvmkotlin.data.source.GitsRepository
import id.gits.gitsmvvmkotlin.util.NavigationParamGlobal
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoRepository

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var eventShowProgress = SingleLiveEvent<Boolean>()
    var eventGlobalMessage = SingleLiveEvent<String>()
    var eventNavigationPage = SingleLiveEvent<NavigationParamGlobal>()

    var verticalList = ObservableField(0)
    var horizontalList = ObservableField(1)

    fun showLogDebug(TAG: String, data: String) = Log.d(TAG, data)
    fun showLogVerbose(TAG: String, data: String) = Log.v(TAG, data)
    fun showLogError(TAG: String, errorMessage: String) = Log.e(TAG, errorMessage)


    @ChocoRepository
    lateinit var gitsRepository: GitsRepository

    open fun start() {}
    open fun onClearDisposable() {}
}