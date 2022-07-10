package com.example.newsapp.data.cache.database

import androidx.room.TypeConverter
import com.example.newsapp.data.models.Source

class TypeConverter {

    @TypeConverter
    fun fromSource(source: Source): String? {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

}