package com.example.myapplication.repository

import com.example.myapplication.data.dto.PhotoDetailDTO
import com.example.myapplication.entity.DetailPhotoEntity

object DetailPhotoMapper {
    fun mapperToResponseEntity(item: PhotoDetailDTO): DetailPhotoEntity {
        return item.run {
            val tags = this.tags.map { it.title }

            DetailPhotoEntity(
                username = this.user.username,
                id = this.id,
                thumb = this.urls.thumb,
                description = this.description ?: "",
                tags = tags,
                downloads = this.links.download
            )
        }
    }
}