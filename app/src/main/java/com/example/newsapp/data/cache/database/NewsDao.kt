package com.example.newsapp.data.cache.database

import androidx.room.*
import com.example.newsapp.data.models.ArticlesItem

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(articlesItem: ArticlesItem)

    @Query("SELECT * FROM newsTable")
    suspend fun news(): MutableList<ArticlesItem>

    @Delete
    suspend fun deleteNews(article: ArticlesItem)

    @Query("SELECT * FROM newsTable WHERE title LIKE :searchQuery OR description LIKE :searchQuery COLLATE NOCASE")
    suspend fun searchNews(searchQuery: String): MutableList<ArticlesItem>

}