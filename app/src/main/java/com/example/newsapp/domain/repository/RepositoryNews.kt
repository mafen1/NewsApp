package com.example.newsapp.domain.repository

import com.example.newsapp.data.models.ResponseNews
import retrofit2.Response

interface RepositoryNews {
    suspend fun news(): Response<ResponseNews>
}