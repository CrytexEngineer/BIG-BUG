package id.gits.gitsmvvmkotlin.data.source.remote

import android.util.Log
import com.example.basemvvm.base.data.model.Article
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.gits.gitsmvvmkotlin.base.BaseApiModel
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.util.GitsNullAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by irfanirawansukirman on 26/01/18.
 */
object GitsRemoteDataSource : GitsDataSource {

    private val articleListAdapter: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(Article::class.java, GitsNullAdapter())
            .create()
    }

    private var compositeDisposable: CompositeDisposable? = null

    override fun getArticles(callback: GitsDataSource.GetArticlesCallback,pageSize:Int,page:Int) {
        GitsApiService.getApiService
            .getArticles(pageSize,page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiCallback<BaseApiModel<List<Article>>>() {
                override fun onSuccess(model: BaseApiModel<List<Article>>) {
                    val oldData = model.results
                    val newData = ArrayList<Article>()
                    for (i in 0 until (oldData?.size?:0)) {
                        oldData?.get(i)?.let {
                            newData.add(
                                it
                            )
                        }
                    }

                    callback.onSuccess(newData)
                }

                override fun onFailure(code: Int, errorMessage: String) {
                    callback.onFailed(code, errorMessage)
                }

                override fun onFinish() {
                    callback.onFinish()
                }

                override fun onStartObserver(disposable: Disposable) {
                    addSubscribe(disposable)
                }
            })
    }

    override fun saveArticle(article: List<Article>) {
        // Tidak digunakan
    }

    override fun onClearDisposables() {
        compositeDisposable?.clear()
    }

    fun addSubscribe(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
            compositeDisposable?.add(disposable)
        }
    }
}