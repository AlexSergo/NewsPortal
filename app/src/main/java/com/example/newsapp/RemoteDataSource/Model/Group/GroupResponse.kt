package com.example.newsapp.RemoteDataSource.Model.Group

import com.example.newsapp.LocalDataSource.Model.Group.Group
import com.google.gson.annotations.SerializedName

data class GroupResponse(
    @SerializedName("group")
    val groups: List<Group>
)
