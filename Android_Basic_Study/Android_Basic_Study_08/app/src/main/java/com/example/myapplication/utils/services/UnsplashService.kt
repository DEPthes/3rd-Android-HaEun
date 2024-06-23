package com.example.myapplication.utils.services

import com.example.myapplication.data.RandomPhotoDTO
import com.example.myapplication.data.UnsplashDTOItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
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
}