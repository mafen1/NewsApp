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

    @Query("Select * from newsTable where title like :searchQuery or description like :searchQuery COLLATE NOCASE")
    suspend fun searchNews(searchQuery: String): MutableList<ArticlesItem>

}