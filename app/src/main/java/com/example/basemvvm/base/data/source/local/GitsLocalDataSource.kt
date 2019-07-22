package id.gits.gitsmvvmkotlin.data.source.local

import android.annotation.SuppressLint
import android.content.Context
import com.example.basemvvm.base.data.model.Article
import com.example.basemvvm.base.data.source.local.GitsAppDatabase
import id.gits.gitsmvvmkotlin.data.source.GitsDataSource
import id.gits.gitsmvvmkotlin.data.source.local.article.ArticleDao
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GitsLocalDataSource(context: Context) : GitsDataSource {

    private var compositeDisposable: CompositeDisposable? = null
    private var status = false

    private val articleDao: ArticleDao by lazy {
        GitsAppDatabase.getInstance(context).articleDao()
    }

    override fun onClearDisposables() {
        compositeDisposable?.clear()
    }

    @SuppressLint("CheckResult")
    override fun getArticles(callback: GitsDataSource.GetArticlesCallback,page:Int,pageSize:Int) {
        articleDao.getArticleList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Article>> {
                override fun onSuccess(t: List<Article>) {
                    callback.onSuccess(t)
                }

                override fun onSubscribe(d: Disposable) {
                    addSubscribe(d)
                }

                override fun onError(e: Throwable) {
                    callback.onHideProgressDialog()
                    callback.onFailed(404, e.message)
                }
            })
    }

    @SuppressLint("CheckResult")
    override fun saveArticle(article: List<Article>) {
        Completable.fromAction { articleDao.insertAllArticles(article) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // Success block
            }, { error ->
                // Error block
            })
    }

    fun addSubscribe(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
            compositeDisposable?.add(disposable)
        }
    }

}