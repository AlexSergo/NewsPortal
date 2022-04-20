package com.example.newsapp.LocalDataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapp.Model.Post.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun dao(): NewsDao

    companion object {
        @Volatile
        var database: NewsDatabase? = null

        fun getInstance(context: Context): NewsDatabase? {
            if (database == null) {
                synchronized(this) {
                    var db = Room.databaseBuilder(
                        context.applicationContext,
                        NewsDatabase::class.java, "news_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    database = db
                    return db
                }
            }
            return database
        }
    }
}