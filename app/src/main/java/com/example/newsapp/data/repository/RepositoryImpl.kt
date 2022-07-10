package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.cache.database.NewsDao
import com.example.newsapp.data.models.ArticlesItem
import com.example.newsapp.data.models.ResponseNews
import com.example.newsapp.domain.repository.RepositoryNews
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao
) : RepositoryNews {

    override suspend fun news(): Response<ResponseNews> = apiService.fetchNews()

    override suspend fun addNews(articlesItem: ArticlesItem) = newsDao.addNews(articlesItem)

    override suspend fun getNews(): MutableList<ArticlesItem> = newsDao.getNews()

    override suspend fun deleteNews() = newsDao.deleteNews()

}