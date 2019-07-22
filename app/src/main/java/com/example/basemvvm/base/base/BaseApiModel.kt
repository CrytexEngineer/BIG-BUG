package id.gits.gitsmvvmkotlin.base

import com.example.basemvvm.base.data.model.Source
import com.google.gson.annotations.SerializedName

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

data class BaseApiModel<T>(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: T? = null,
    //@SerializedName("source")
   // val source: Source?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("content")
    val content: String?,

// Remove code below if project is running
    var page: Int,
    var total_results: Int,
    var total_pages: Int,

    @SerializedName("articles")
    var results: T? = null
)