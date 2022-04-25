package com.example.newsapp.LocalDataSource.Model.Group

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("subs_amount")
    val subsAmount: Int,
    @SerializedName("admin_id")
    val adminId: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)
