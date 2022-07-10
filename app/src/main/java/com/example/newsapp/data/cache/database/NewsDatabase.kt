package com.example.newsapp.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.models.ArticlesItem

@Database(entities = [ArticlesItem::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}