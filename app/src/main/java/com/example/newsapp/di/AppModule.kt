package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.cache.database.NewsDao
import com.example.newsapp.data.cache.database.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideBaseUrl() = "https://newsapi.org"

    @Provides
    @Singleton
    fun provideRetrofitClient(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideNewsDatabase( @ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(context, NewsDatabase::class.java,"news_database")
            .build()

    @Provides
    @Singleton
    fun provideNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao()
}