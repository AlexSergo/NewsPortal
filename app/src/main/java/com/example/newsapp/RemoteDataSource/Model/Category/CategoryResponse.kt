package com.example.newsapp.RemoteDataSource.Model.Category

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updated_at: String,
    @SerializedName("created_at")
    val created_at: String
)
