package com.example.android_basic_study_06

data class ItemDTO(
    val limit: Int,
    val products: List<ProductDTO>,
    val skip: Int,
    val total: Int
)

data class ProductDTO(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)