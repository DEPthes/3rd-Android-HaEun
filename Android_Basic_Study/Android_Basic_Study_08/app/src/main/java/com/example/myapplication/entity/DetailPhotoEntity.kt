package com.example.myapplication.entity

import com.example.myapplication.data.dto.Tag

data class DetailPhotoEntity(
    val username: String,
    val id: String,
    val thumb: String,
    val description: String,
    val tags: List<Tag>,
    val downloads: String
)