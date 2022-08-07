package com.example.newsapp.data.cache.database

import androidx.room.Dao
import androidx.room.*
import com.example.newsapp.data.models.ArticlesItem

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(articlesItem: ArticlesItem)

    @Query("SELECT * FROM newsTable")
    suspend fun getNews(): MutableList<ArticlesItem>

    @Query("DELETE FROM newsTable")
    suspend fun deleteNews()

    @Query("SELECT * FROM 'newsTable' WHERE title LIKE :searchQuery")
    suspend fun searchNews(searchQuery: String): List<ArticlesItem>

}