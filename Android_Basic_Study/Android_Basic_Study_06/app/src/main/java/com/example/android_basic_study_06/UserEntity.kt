package com.example.android_basic_study_06

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    @ColumnInfo
    val email: String,
    @ColumnInfo
    val password: String
)