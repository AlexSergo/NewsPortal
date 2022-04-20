package com.example.newsapp.Model.Group

import com.google.gson.annotations.SerializedName

data class ShortGroupEntity(
    val id: Int,
    val categoryId: Int,
    val title: String
)
