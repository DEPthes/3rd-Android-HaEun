package com.example.myapplication.repository

import com.example.myapplication.entity.NewPhotoEntity

interface PhotoRepository {
    suspend fun getPhoto(
        page: Int,
        per_page: Int,
        order_by: String
    ): Result<List<NewPhotoEntity>>

    suspend fun getRandomPhoto(
        count: Int
    ) : Result<List<NewPhotoEntity>>
}