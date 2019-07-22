package id.gits.gitsmvvmkotlin.data.source

import com.example.basemvvm.base.data.model.Article

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

open class GitsRepository(private val remoteDataSource: GitsDataSource,
                          private val localDataSource: GitsDataSource) : GitsDataSource {

    override fun onClearDisposables() {
        remoteDataSource.onClearDisposables()
        localDataSource.onClearDisposables()
    }

    override fun saveArticle(article: List<Article>) {
        localDataSource.saveArticle(article)
    }

    override fun getArticles(callback: GitsDataSource.GetArticlesCallback,pageSize:Int,page:Int) {
        remoteDataSource.getArticles(object : GitsDataSource.GetArticlesCallback {
            override fun onShowProgressDialog() {

            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: List<Article>) {
                saveArticle(data)
                loadArticles(callback,pageSize,page)
            }

            override fun onFinish() {
                callback.onFinish()
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                callback.onFailed(statusCode, errorMessage)
            }
        },pageSize,page)
    }

    private fun loadArticles(callback: GitsDataSource.GetArticlesCallback,pageSize:Int,page:Int) {
        localDataSource.getArticles(object : GitsDataSource.GetArticlesCallback {
            override fun onShowProgressDialog() {

            }

            override fun onHideProgressDialog() {

            }

            override fun onSuccess(data: List<Article>) {
                callback.onSuccess(data)
            }

            override fun onFinish() {
                callback.onFinish()
            }

            override fun onFailed(statusCode: Int, errorMessage: String?) {
                callback.onFailed(statusCode, errorMessage)
            }
        },pageSize,page)
    }

}