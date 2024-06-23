package com.example.myapplication.utils

import com.example.myapplication.BuildConfig
import com.example.myapplication.entity.NewPhotoEntity
import org.json.JSONObject
import retrofit2.create

class PhotoRepositoryImpl : PhotoRepository {
    private val service = RetrofitClient.getInstance().create(UnsplashService::class.java)
    override suspend fun getPhoto(
        page: Int,
        per_page: Int,
        order_by: String
    ): Result<List<NewPhotoEntity>> {
        val res =
            service.getPhoto("v1", "Client-Id ${BuildConfig.UNSPLASH_ACCESS_KEY}",
                10, 10, "latest")
        return if (res.isSuccessful) {
            if (res.body() == null) Result.success(listOf())
            else Result.success(PhotoMapper.mapperToResponseEntity(res.body()!!))
        }
        else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }
}