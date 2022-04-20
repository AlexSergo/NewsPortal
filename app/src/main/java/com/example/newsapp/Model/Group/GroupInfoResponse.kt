package com.example.newsapp.Model.Group

import com.example.newsapp.Model.Post.PostResponse
import com.google.gson.annotations.SerializedName

data class GroupInfoResponse(
    @SerializedName("group")
    val group: Group,
    @SerializedName("posts")
    val posts: List<PostResponse>
)
