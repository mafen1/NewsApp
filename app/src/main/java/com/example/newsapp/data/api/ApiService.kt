package com.example.newsapp.data.api

import com.example.newsapp.data.models.ResponseNews
import com.example.notesapp.core.ConstVariables
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun fetchNews(
        @Query("country") country: String = ConstVariables.country,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = ConstVariables.API_KEY
    ): Response<ResponseNews>

}