package com.example.newsapp.Model.User

data class UserRegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
