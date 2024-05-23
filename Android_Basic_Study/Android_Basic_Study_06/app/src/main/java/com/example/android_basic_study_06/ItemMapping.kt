package com.example.android_basic_study_06

object ItemMapping {
    fun mapperToResponseEntity(item: ItemDTO): List<ItemEntity> {
        return item.products.map {
            ItemEntity(price = it.price, thumbnail = it.thumbnail, title = it.title)
        }
    }
}