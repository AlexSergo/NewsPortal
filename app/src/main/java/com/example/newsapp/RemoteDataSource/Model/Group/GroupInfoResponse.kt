package com.example.newsapp.RemoteDataSource.Model.Group

import com.example.newsapp.LocalDataSource.Model.Group.Group
import com.example.newsapp.RemoteDataSource.Model.Post.PostResponse
import com.google.gson.annotations.SerializedName

data class GroupInfoResponse(
    @SerializedName("group")
    val group: Group,
    @SerializedName("posts")
    val posts: List<PostResponse>
)
