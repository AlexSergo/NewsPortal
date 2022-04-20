package com.example.newsapp.Model

import com.example.newsapp.LocalDataSource.NewsDao
import com.example.newsapp.Model.Category.CategoryEntity
import com.example.newsapp.Model.Category.CategoryResponse
import com.example.newsapp.Model.Group.GroupResponse
import com.example.newsapp.Model.Post.PostResponse
import com.example.newsapp.Model.Comment.CommentResponse
import com.example.newsapp.Model.Group.GroupEntity
import com.example.newsapp.Model.Group.GroupInfoResponse
import com.example.newsapp.Model.Group.ShortGroupEntity
import com.example.newsapp.Model.Like.LikeResponse
import com.example.newsapp.Model.Post.PostEntity
import com.example.newsapp.Model.User.LoginResponse
import com.example.newsapp.Model.User.UserEntity
import com.example.newsapp.Model.User.UserLoginRequest
import com.example.newsapp.Model.User.UserRegisterRequest
import com.example.newsapp.RemoteDataSource.NewsApi

object NewsDataMapper{

    fun mapRemoteCategoryToEntity(category: CategoryResponse): CategoryEntity {
        return CategoryEntity(category.name)
    }

    fun mapUserEntityToLoginRequest(user: UserEntity): UserLoginRequest {
        return (UserLoginRequest(user.email, user.password))
    }

    fun mapRemoteUserToEntity(user: LoginResponse): UserEntity {
        return UserEntity(name = user.user.name, email = user.user.email, token = user.token.substring(3))
    }

    fun mapUserEntityToRegisterRequest(user: UserEntity): UserRegisterRequest {
        return UserRegisterRequest(user.name, user.email, user.password)
    }

    fun mapRemoteGroupsToEntityList(groups: GroupResponse): List<ShortGroupEntity> {
        val result = mutableListOf<ShortGroupEntity>()
        for (group in groups.groups){
            result.add(ShortGroupEntity(
                id = group.id,
                categoryId = group.categoryId,
                title = group.title))
        }
        return result
    }

    fun mapRemoteGroupToEntity(response: GroupInfoResponse): GroupEntity {
        return GroupEntity(
            id = response.group.id,
            categoryId = response.group.categoryId,
            title = response.group.title,
            subscribersAmount = response.group.subsAmount,
            posts = mapRemotePostsToEntityList(response.posts)
        )
    }

    private fun mapRemotePostsToEntityList(posts: List<PostResponse>): List<PostEntity> {
        val result = mutableListOf<PostEntity>()
        for (post in posts)
            result.add(PostEntity(
                id = post.id,
                groupId = post.groupId,
                userId = post.userId,
                title = post.title,
                description = post.description, shortDesc = post.shortDesc,
                likeAmount = post.likeAmount, seeAmount = post.seeAmount,
                commentAmount = post.commentAmount
            ))
        return result
    }
}

class Repository(private val newsApi: NewsApi, private val newsDao: NewsDao) {

    suspend fun login(user: UserEntity): UserEntity {
        val result = newsApi.login(NewsDataMapper.mapUserEntityToLoginRequest(user))

        return NewsDataMapper.mapRemoteUserToEntity(result)
    }

    suspend fun register(user: UserEntity): UserEntity{
        val result = newsApi.register(NewsDataMapper.mapUserEntityToRegisterRequest(user))
        return NewsDataMapper.mapRemoteUserToEntity(result)
    }

    suspend fun getAllCategories(): List<CategoryEntity> {
        val categories = newsApi.getAllCategories()
        val result = mutableListOf<CategoryEntity>()
        for (category in categories)
            result.add(NewsDataMapper.mapRemoteCategoryToEntity(category))
        return result
    }

    suspend fun getCategory(id: Int): CategoryEntity {
        return NewsDataMapper.mapRemoteCategoryToEntity(newsApi.getCategory(id))
    }

    suspend fun getGroupsForCategory(id: Int): List<ShortGroupEntity>{
        return NewsDataMapper.mapRemoteGroupsToEntityList(newsApi.getGroupsForCategory(id))
    }

    suspend fun getGroupById(id: Int, token: String): GroupEntity{
        return NewsDataMapper.mapRemoteGroupToEntity(newsApi.getGroupById(id, "Bearer $token"))
    }

   suspend fun getPostsForGroup(id: Int): List<PostResponse>{
       return newsApi.getPostsForGroup(id).posts
   }

    suspend fun postNewPost(post: PostResponse) {
        newsApi.postNewPost(post)
    }

    suspend fun updatePost(id: Int) {
        newsApi.updatePost(id)
    }

    suspend fun deletePost(id: Int) {
        newsApi.deletePost(id)
    }

    suspend fun getLikesForPost(postId: Int): LikeResponse {
        return newsApi.getLikesForPost(postId)
    }

    suspend fun increaseLikesForPost(postId: Int){
        newsApi.increaseLikesForPost(postId)
    }

    suspend fun getCommentsForPost(postId: Int): CommentResponse {
        return newsApi.getCommentsForPost(postId)
    }

    suspend fun postNewComment(comment: CommentResponse){
        newsApi.postNewComment(comment)
    }



    suspend fun insertPostsToLocalDataSource(posts: List<PostEntity>){
        newsDao.insertPostsToLocalDataSource(posts)
    }

    suspend fun deletePostsFromLocalDataSource(){
        newsDao.deletePostsFromLocalDataSource()
    }
}
