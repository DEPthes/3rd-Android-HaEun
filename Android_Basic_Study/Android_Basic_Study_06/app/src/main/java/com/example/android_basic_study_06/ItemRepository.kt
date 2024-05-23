package com.example.android_basic_study_06

interface ItemRepository {
    suspend fun getItem(searchItem: String): Result<List<ItemEntity>>
}