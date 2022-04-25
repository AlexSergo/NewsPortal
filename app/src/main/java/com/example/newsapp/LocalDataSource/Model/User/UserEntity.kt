package com.example.newsapp.LocalDataSource.Model.User

data class UserEntity(
    val name: String,
    val email: String,
    val password: String = "",
    var token: String = ""
)
