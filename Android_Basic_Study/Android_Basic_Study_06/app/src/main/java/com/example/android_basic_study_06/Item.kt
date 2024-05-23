package com.example.android_basic_study_06

data class Item(
    val imgUrl: String,
    val title: String,
    val price: Int
)

data class ItemEntity(
    val thumbnail: String,
    val title: String,
    val price: Int
)