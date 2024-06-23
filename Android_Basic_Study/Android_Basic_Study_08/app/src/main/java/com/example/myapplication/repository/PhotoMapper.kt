package com.example.myapplication.repository

import com.example.myapplication.data.dto.UnsplashDTOItem
import com.example.myapplication.entity.NewPhotoEntity

object PhotoMapper {
    fun mapperToResponseEntity(item: List<UnsplashDTOItem>): List<NewPhotoEntity> {
        val list = mutableListOf<NewPhotoEntity>()
        item.forEach {
            val id = it.id
            val url = it.urls.thumb
            val des = it.description ?: ""

            list.add(NewPhotoEntity(id, url, des))
        }
        return list
    }
}