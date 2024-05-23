package com.example.android_basic_study_06

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteItem")
data class FavoriteItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo
    val price: Int,
    @ColumnInfo
    val thumbnail: String,
    @ColumnInfo
    val title: String
)