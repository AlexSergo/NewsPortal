package com.example.newsapp.LocalDataSource.Model.Group

import com.example.newsapp.LocalDataSource.Model.Post.PostEntity

data class GroupEntity(
    val id: Int,
    val categoryId: Int,
    val title: String,
    val subscribersAmount: Int,
    val posts: List<PostEntity>
)