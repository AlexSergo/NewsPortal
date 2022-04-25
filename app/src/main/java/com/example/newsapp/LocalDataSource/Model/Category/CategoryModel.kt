package com.example.newsapp.LocalDataSource.Model.Category

import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("name")
    val name: String
)