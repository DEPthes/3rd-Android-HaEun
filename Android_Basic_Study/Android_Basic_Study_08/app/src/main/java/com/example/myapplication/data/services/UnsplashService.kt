package com.example.myapplication.data.services

import com.example.myapplication.data.dto.PhotoDetailDTO
import com.example.myapplication.data.dto.RandomPhotoDTO
import com.example.myapplication.data.dto.UnsplashDTOItem
import com.example.myapplication.entity.DetailPhotoEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashService {
    @GET("/photos")
    suspend fun getPhoto (
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ) : Response<List<UnsplashDTOItem>>

    @GET("/photos/random")
    suspend fun getRandomPhoto (
        @Query("count") count: Int,
    ) : Response<List<RandomPhotoDTO>>

    @GET("/photos/{id}")
    suspend fun getPhotoDetail (
        @Path(value = "id") id: String
    ) : Response<PhotoDetailDTO>
}