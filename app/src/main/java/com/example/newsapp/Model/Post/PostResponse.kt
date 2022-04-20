package com.example.newsapp.Model.Post

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("group_id")
    val groupId: Int,
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("short_desc")
    val shortDesc: String,
    @SerializedName("like_amount")
    val likeAmount: Int,
    @SerializedName("see_amount")
    val seeAmount: Int,
    @SerializedName("comm_amount")
    val commentAmount: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("is_liked")
    val isLiked: Boolean
)
