package com.example.newsapp.Model.Like

import com.google.gson.annotations.SerializedName

data class LikeResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("post_id")
    val postId: Int,
    @SerializedName("updated_at")
    val updatedAt: Int,
    @SerializedName("created_at")
    val createdAt: Int
)
