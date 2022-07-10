package com.example.newsapp.domain.repository

import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.data.models.ResponseNews
import retrofit2.Response

interface RepositoryNews {
    suspend fun news(): Response<ResponseNews>
    suspend fun addNews(articlesItem: ArticlesItem)
    suspend fun getNews(): List<ArticlesItem>
    suspend fun deleteNews()
}