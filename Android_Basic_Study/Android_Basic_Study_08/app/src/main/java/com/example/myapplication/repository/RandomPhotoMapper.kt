package com.example.myapplication.repository

import com.example.myapplication.data.dto.RandomPhotoDTO
import com.example.myapplication.entity.NewPhotoEntity

object RandomPhotoMapper {
    fun mapperToResponseEntity(item: List<RandomPhotoDTO>): List<NewPhotoEntity> {
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