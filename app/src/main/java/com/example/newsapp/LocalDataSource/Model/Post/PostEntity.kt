package com.example.newsapp.LocalDataSource.Model.Post

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("group_name")
    val groupName: String,
    @SerializedName("group_id")
    val groupId: Int,
    @SerializedName("user_id")
    val userId : Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("short_desc")
    val shortDesc: String,
    @SerializedName("like_amount")
    val likeAmount: Int,
    @SerializedName("see_amount")
    val seeAmount: Int,
    @SerializedName("comm_amount")
    val commentAmount: Int,
    @SerializedName("is_liked")
    val isLiked: Boolean
)
