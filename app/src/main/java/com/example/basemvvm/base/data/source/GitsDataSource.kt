package id.gits.gitsmvvmkotlin.data.source

import com.example.basemvvm.base.data.model.Article
import id.gits.gitsmvvmkotlin.base.BaseDataSource
import id.gits.gitsmvvmkotlin.base.BaseDataSource.GitsResponseCallback

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

interface GitsDataSource: BaseDataSource {

    fun getArticles(callback: GetArticlesCallback,pageSize:Int,page:Int)

    fun saveArticle(article: List<Article>)

    interface GetArticlesCallback : GitsResponseCallback<List<Article>>

    interface GetArticlesByIdCallback : GitsResponseCallback<Article>

}