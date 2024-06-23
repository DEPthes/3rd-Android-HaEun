package com.example.myapplication.data.dto

data class RandomPhotoDTO(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val description: String,
    val downloads: Int,
    val exif: Exif,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val location: Location,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)

data class Exif(
    val aperture: String,
    val exposure_time: String,
    val focal_length: String,
    val iso: Int,
    val make: String,
    val model: String
)

data class Location(
    val city: String,
    val country: String,
    val name: String,
    val position: Position
)

data class Position(
    val latitude: Double,
    val longitude: Double
)