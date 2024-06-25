package com.example.myapplication.repository

import com.example.myapplication.data.dto.PhotoDetailDTO
import com.example.myapplication.entity.DetailPhotoEntity

object DetailPhotoMapper {
    fun mapperToResponseEntity(item: PhotoDetailDTO): DetailPhotoEntity {
        return item.run {
            // map => mapping 해주는 거임 그니까 tag 의 title 이 필요해서 갖고 오는 거
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