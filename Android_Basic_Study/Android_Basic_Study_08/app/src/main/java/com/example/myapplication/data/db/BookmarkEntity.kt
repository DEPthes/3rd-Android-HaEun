package com.example.myapplication.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Bookmark")
data class BookmarkEntity(
    @PrimaryKey
    val id : String,
    @ColumnInfo
    val url : String
)