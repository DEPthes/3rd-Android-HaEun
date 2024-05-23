package com.example.android_basic_study_06

import androidx.room.Query
import kotlinx.coroutines.repackaged.net.bytebuddy.agent.VirtualMachine
import retrofit2.http.GET

interface Service {
    @GET("products/search")
    suspend fun getItem(
        @Query("q") q: String
    ): VirtualMachine.ForHotSpot.Connection.Response<ItemDTO>
}