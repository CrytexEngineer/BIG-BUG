package id.gits.gitsmvvmkotlin.data.source.local.article

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import com.example.basemvvm.base.data.model.Article
import io.reactivex.Single

@Dao
interface ArticleDao {

    @android.arch.persistence.room.Query("SELECT * FROM article WHERE id = :id")
    fun getArticleById(id: Int): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllArticles(articles: List<Article>)

    @android.arch.persistence.room.Query("DELETE FROM article")
    fun deleteAllHeroes()

    @android.arch.persistence.room.Query("SELECT * FROM article")
    fun getArticleList(): Single<List<Article>>

    @android.arch.persistence.room.Query("SELECT * FROM article")
    fun getAllArticles(): List<Article>

}