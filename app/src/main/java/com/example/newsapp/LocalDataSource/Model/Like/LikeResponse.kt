package com.example.newsapp.LocalDataSource.Model.Like

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int = 0
)
