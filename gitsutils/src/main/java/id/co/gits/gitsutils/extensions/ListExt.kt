package id.co.gits.gitsutils.extensions

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

fun RecyclerView.setupLinearLayoutManager(orientation: Int, isReversed: Boolean) {
    layoutManager = LinearLayoutManager(
            context,
            if (orientation == 1) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL,
            isReversed)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}

fun RecyclerView.setupGridLayoutManager(span: Int, orientation: Int, isReversed: Boolean) {
    layoutManager = GridLayoutManager(context, span)
    setHasFixedSize(true)
    itemAnimator = DefaultItemAnimator()
    setItemViewCacheSize(30)
    isDrawingCacheEnabled = true
    drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
}