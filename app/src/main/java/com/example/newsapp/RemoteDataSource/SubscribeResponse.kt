package com.example.newsapp.RemoteDataSource

import com.google.gson.annotations.SerializedName

data class SubscribeResponse(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("group_id")
    val groupId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int
)
