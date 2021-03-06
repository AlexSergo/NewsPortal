package com.example.newsapp.RemoteDataSource.Model.Comment

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)