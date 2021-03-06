package com.example.newsapp.ViewModel

import android.content.Context
import com.example.newsapp.LocalDataSource.NewsDao
import com.example.newsapp.LocalDataSource.NewsDatabase
import com.example.newsapp.LocalDataSource.Model.Repository
import com.example.newsapp.RemoteDataSource.NewsApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RepositoryInitializer{

    private var api: NewsApi? = null
    private var dao: NewsDao? = null
    private lateinit var repository: Repository

    fun getRepository(context: Context): Repository {
        if (api == null) {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("http://news-portal-api.std-926.ist.mospolytech.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            api = retrofit.create(NewsApi::class.java)
        }
        if (dao == null)
            dao = NewsDatabase.getInstance(context)?.dao()

        if (api != null && dao != null)
            repository = Repository(api!!, dao!!)
        return repository
    }
}