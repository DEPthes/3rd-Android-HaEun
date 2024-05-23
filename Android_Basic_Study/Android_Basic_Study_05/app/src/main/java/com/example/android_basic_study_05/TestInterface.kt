package com.example.android_basic_study_05

import com.example.android_basic_study_05.RetrofitClient.URL_MOVIE
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TestInterface {
    @GET("$URL_MOVIE")
    suspend fun getMovie(
        @Query("key") key: String,
        @Query("targetDT") targetDT: String
    ) : Call<SearchDailyBoxOfficeDTO>
}