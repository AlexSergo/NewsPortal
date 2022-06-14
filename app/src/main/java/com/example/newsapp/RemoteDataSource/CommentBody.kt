package com.example.newsapp.RemoteDataSource

import com.google.gson.annotations.SerializedName

data class CommentBody(
    @SerializedName("post_id")
    val postId: Number,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: String
)