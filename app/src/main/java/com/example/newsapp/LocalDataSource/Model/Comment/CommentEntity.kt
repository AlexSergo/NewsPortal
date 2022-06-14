package com.example.newsapp.LocalDataSource.Model.Comment

import com.google.gson.annotations.SerializedName

data class CommentEntity(
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: String
)