package com.example.detailnews.detailpage;

import android.app.Application
import android.os.Handler
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ObservableField
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import id.co.gits.gitsutils.SingleLiveEvent


class MainDetailViewModel(context: Application) : AndroidViewModel(context) {

    val MainDetailList: ObservableList<MainDetailModel> = ObservableArrayList()

    // TODO if template have an error, please reimport SingleLiveEvent
    val eventClickItem = SingleLiveEvent<MainDetailModel>()

    val isRequesting = ObservableField(true)
    val isLastPage = ObservableField(false)
    val showProgress = SingleLiveEvent<Boolean>()

    val eventHideLoadmore = SingleLiveEvent<Boolean>()

    var mPagePosition = 1

    fun start() {
        loadData(reload = true)
    }

    fun refresh() {
        loadData(reload = true)
    }


    fun loadData(reload: Boolean = false) {
        if (reload) {
            resetState();
        }

        // TODO Change this with request from API
        Handler().postDelayed({
            if (mPagePosition <= 5) {
                for (i in 0 until PAGE_SIZE) {
                    MainDetailList.add(MainDetailModel("GITS Indonesia"))
                }
            } else {
                isLastPage.set(true);
            }

            showProgress.value = false
            isRequesting.set(false)
            eventHideLoadmore.value = isLastPage.get()
        }, 2000)
    }


    private fun resetState() {
        isLastPage.set(false);
        showProgress.value = true
        MainDetailList.clear()
        mPagePosition = 1
    }


    companion object {

        const val PAGE_SIZE = 10


        @BindingAdapter("listDataMainDetail")
        @JvmStatic
        fun setListDataMainDetail(recyclerView: RecyclerView, data: List<MainDetailModel>) {
            with(recyclerView.adapter as MainDetailAdapter) {
                replaceData(data)
            }
        }

    }


}

/**
 * TODO add to bindings class
 * function for bindingsList
 *
 * object MainDetailBindings {
 *
 * 	@BindingAdapter("app:listDataMainDetail")
 *     @JvmStatic
 *     fun setListDataMainDetail(recyclerView: RecyclerView, data: List<MainDetailModel>) {
 *         with(recyclerView.adapter as MainDetailAdapter) {
 *             replaceData(data)
 *         }
 *     }
 *
 * }
 *
 **/