package com.example.newsapp.LocalDataSource

import com.google.gson.annotations.SerializedName

data class LikeBody(
    @SerializedName("post_id")
    val postId: Int
)