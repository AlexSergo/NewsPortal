package com.example.newsapp.RemoteDataSource.Model.Comment

import com.google.gson.annotations.SerializedName

data class CommentResponse(
    val comments: List<Comment>
)
