package com.example.newsapp.Model.Category

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CategoryModel(
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("name")
    val name: String
)