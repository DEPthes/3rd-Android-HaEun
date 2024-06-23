package com.example.myapplication.utils

import com.example.myapplication.data.UnsplashDTO
import com.example.myapplication.data.UnsplashDTOItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UnsplashService {
    @GET("/photos")
    suspend fun getPhoto (
        @Header("Accept-Version") version: String,
        @Header("Authorization") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int,
        @Query("order_by") order_by: String
    ) : Response<List<UnsplashDTOItem>>
}