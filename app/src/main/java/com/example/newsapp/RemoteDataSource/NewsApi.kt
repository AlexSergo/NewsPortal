package com.example.newsapp.RemoteDataSource

import com.example.newsapp.Model.Category.CategoryResponse
import com.example.newsapp.Model.Comment.CommentResponse
import com.example.newsapp.Model.Group.GroupInfoResponse
import com.example.newsapp.Model.Group.GroupResponse
import com.example.newsapp.Model.Like.LikeResponse
import com.example.newsapp.Model.Post.PostList
import com.example.newsapp.Model.Post.PostResponse
import com.example.newsapp.Model.User.LoginResponse
import com.example.newsapp.Model.User.UserLoginRequest
import com.example.newsapp.Model.User.UserRegisterRequest
import retrofit2.http.*

interface NewsApi {
    @GET("/api/category")
    suspend fun getAllCategories(): List<CategoryResponse>
    @GET("/api/category/{id}")
    suspend fun getCategory(@Path("id") id: Int): CategoryResponse

    @GET("/api/groups/category/{id}")
    suspend fun getGroupsForCategory(@Path("id") id: Int): GroupResponse
    @GET("/api/groups/{id}")
    suspend fun getGroupById(@Path("id") id: Int,
                             @Header("Authorization") token: String): GroupInfoResponse
    @Headers("Content-Type: application/json")
    @POST("/api/groups")
    suspend fun postNewGroup(@Body group: GroupResponse)
    @PUT("/api/groups/{id}")
    suspend fun updateGroup(@Path("id") id: Int)

    @GET("/api/posts/group/{id}")
    suspend fun getPostsForGroup(@Path("id") id: Int): PostList
    @Headers("Content-Type: application/json")
    @POST("/api/posts")
    suspend fun postNewPost(@Body post: PostResponse)
    @PUT("/api/posts/{id}")
    suspend fun updatePost(@Path("id") id: Int)
    @DELETE("/api/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)

    @GET("/api/posts/{id}/likes")
    suspend fun getLikesForPost(@Path("id") id: Int): LikeResponse
    @PUT("/api/likes/{id}")
    suspend fun increaseLikesForPost(@Path("id") id: Int)

    @GET("/api/post/{id}/comments")
    suspend fun getCommentsForPost(@Path("id") id: Int): CommentResponse
    @Headers("Content-Type: application/json")
    @POST("/api/comments")
    suspend fun postNewComment(@Body comment: CommentResponse)

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    suspend fun login(@Body user: UserLoginRequest): LoginResponse
    @Headers("Content-Type: application/json")
    @POST("/api/register")
    suspend fun register(@Body user: UserRegisterRequest): LoginResponse
}