package com.example.newsapp.RemoteDataSource

import com.example.newsapp.LocalDataSource.LikeBody
import com.example.newsapp.LocalDataSource.Model.Comment.CommentEntity
import com.example.newsapp.RemoteDataSource.Model.Category.CategoryResponse
import com.example.newsapp.RemoteDataSource.Model.Comment.CommentResponse
import com.example.newsapp.RemoteDataSource.Model.Group.GroupInfoResponse
import com.example.newsapp.RemoteDataSource.Model.Group.GroupResponse
import com.example.newsapp.LocalDataSource.Model.Like.LikeResponse
import com.example.newsapp.RemoteDataSource.Model.Comment.Comment
import com.example.newsapp.RemoteDataSource.Model.Post.PostList
import com.example.newsapp.RemoteDataSource.Model.Post.PostResponse
import com.example.newsapp.RemoteDataSource.Model.User.LoginResponse
import com.example.newsapp.RemoteDataSource.Model.User.UserLoginRequest
import com.example.newsapp.RemoteDataSource.Model.User.UserRegisterRequest
import retrofit2.Response
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
    @POST("/api/subscribes/group/{id}")
    suspend fun subscribe(@Path("id") groupId: Int,
                          @Header("Authorization") token: String): SubscribeResponse
    @DELETE("/api/subscribes/group/{id}")
    suspend fun unsubscribe(@Path("id") groupId: Int,
                          @Header("Authorization") token: String): SubscribeResponse
    @Headers("Content-Type: application/json")
    @GET("/api/subscribes")
    suspend fun getSubscribes(@Header("Authorization") token: String): List<SubscribeResponse>

    @GET("/api/posts/group/{id}")
    suspend fun getPostsForGroup(@Path("id") id: Int): PostList
    @POST("/api/posts")
    suspend fun postNewPost(@Body post: PostResponse)
    @PUT("/api/posts/{id}")
    suspend fun updatePost(@Path("id") id: Int)
    @DELETE("/api/posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)

    @GET("/api/posts/{id}/likes")
    suspend fun getLikesForPost(@Path("id") id: Int): LikeResponse
    @Headers("Content-Type: application/json")
    @POST("/api/likes")
    suspend fun likePost(@Body likeBody: LikeBody, @Header("Authorization") token: String): Response<LikeResponse>
    @DELETE("/api/likes/{id}")
    suspend fun dislikePost(@Path("id") postId: Int,
                            @Header("Authorization") token: String): Response<LikeResponse>
    @GET("/api/comment/{id}")
    suspend fun getCommentsForPost(@Path("id") id: Int,
                                   @Header("Authorization") token: String): List<Comment>
    @Headers("Content-Type: application/json")
    @POST("/api/comment")
    suspend fun createComment(@Body commentBody: CommentBody,
                               @Header("Authorization") token: String): Comment

    @Headers("Content-Type: application/json")
    @POST("/api/login")
    suspend fun login(@Body user: UserLoginRequest): LoginResponse
    @Headers("Content-Type: application/json")
    @POST("/api/register")
    suspend fun register(@Body user: UserRegisterRequest): LoginResponse
}