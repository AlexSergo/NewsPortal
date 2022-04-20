package com.example.newsapp.Model.User

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val user: UserResponse,
    @SerializedName("token")
    val token: String
)