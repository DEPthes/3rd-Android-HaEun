package com.example.myapplication.utils

import com.example.myapplication.entity.NewPhotoEntity

interface PhotoRepository {
    suspend fun getPhoto(
        page: Int,
        per_page: Int,
        order_by: String
    ): Result<List<NewPhotoEntity>>
}