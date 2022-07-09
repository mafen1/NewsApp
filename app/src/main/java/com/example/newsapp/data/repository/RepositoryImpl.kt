package com.example.newsapp.data.repository

import com.example.newsapp.data.api.ApiService
import com.example.newsapp.data.models.ResponseNews
import com.example.newsapp.domain.repository.RepositoryNews
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService): RepositoryNews {

    override suspend fun news(): Response<ResponseNews> = apiService.fetchNews()

}