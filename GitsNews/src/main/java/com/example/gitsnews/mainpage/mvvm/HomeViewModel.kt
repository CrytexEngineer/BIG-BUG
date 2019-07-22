package com.example.gitsnews.mainpage.mvvm;

import android.app.Application
import android.databinding.BindingAdapter
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.basemvvm.base.data.model.Article
import id.co.gits.gitsbase.BaseViewModel
import id.co.gits.gitsutils.SingleLiveEvent
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import radhika.yusuf.id.mvvmkotlin.utils.chocohelper.ChocoChips


class HomeViewModel(context: Application) : BaseViewModel(context) {

    var movieListLive = SingleLiveEvent<ArrayList<Article>>()
    var movie= ObservableArrayList<Article>()
    val snackBarMessageRemote =SingleLiveEvent<String>()
    val eventClickItem = SingleLiveEvent<Article>()

    val isRequesting = ObservableField(true)
    val isLastPage = ObservableField(false)
    val showProgress = SingleLiveEvent<Boolean>()

    val eventHideLoadmore = SingleLiveEvent<Boolean>()
    var mPagePosition = 1

    override fun start() {
        super.start()
        ChocoChips.inject(this)
        loadData(reload = true)
    }

    fun refresh() {
        loadData(reload = true)
    }

    override fun onClearDisposable() {
        super.onClearDisposable()
        gitsRepository.onClearDisposables()
    }


    fun loadData(reload: Boolean = false) {
        if (reload) {
            resetState();
        }


        gitsRepository.getArticles(object : GitsDataSource.GetArticlesCallback {
            override fun onShowProgressDialog() {

            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: List<Article>) {
                movieListLive.apply {
                    if (mPagePosition <= 5) {
                        movie.addAll(data)
                        movieListLive.value = movie

                    } else {
                        isLastPage.set(true)
                        Log.d("TAG", "LAST PAGE")
                    }
                }
            }

            override fun onFinish() {

            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                snackBarMessageRemote.value = errorMessage
            }
        },PAGE_SIZE,mPagePosition)


        showProgress.value = false
        isRequesting.set(false)
        eventHideLoadmore.value = isLastPage.get()
    }

    private fun resetState() {
        isLastPage.set(false)
        showProgress.value = true
        movie.clear()
        mPagePosition = 1
    }


    companion object {

        const val PAGE_SIZE = 10

     //   @BindingAdapter("app:listDataMain")
        @JvmStatic
        fun setListDataMain(recyclerView: RecyclerView?, data:ArrayList<Article>?) {
            with(recyclerView?.adapter as HomeAdapter?) {
                Log.d("TAG", recyclerView?.adapter.toString())
                data?.let { this?.replaceData(it) }
            }
        }


    }
}



