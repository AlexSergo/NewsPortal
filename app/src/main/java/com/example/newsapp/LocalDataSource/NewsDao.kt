package com.example.newsapp.LocalDataSource

import androidx.room.*
import com.example.newsapp.LocalDataSource.Model.Post.PostEntity

@Dao
interface NewsDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertPostsToLocalDataSource(posts: List<PostEntity>)
    @Query("DELETE FROM post")
    suspend fun deletePostsFromLocalDataSource()
}