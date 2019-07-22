package com.example.basemvvm.base.data.model

import com.google.gson.annotations.SerializedName

class ArticleList {
    @SerializedName("articles")
    lateinit var articleList: ArrayList<Article>

}