package com.example.myapplication.utils.photos

import android.util.Log
import com.example.myapplication.BuildConfig
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.utils.PhotoMapper
import com.example.myapplication.utils.RandomPhotoMapper
import com.example.myapplication.utils.RetrofitClient
import com.example.myapplication.utils.services.UnsplashService
import org.json.JSONObject

class PhotoRepositoryImpl : PhotoRepository {
    private val service = RetrofitClient.getInstance().create(UnsplashService::class.java)
    override suspend fun getPhoto(
        page: Int,
        per_page: Int,
        order_by: String
    ): Result<List<NewPhotoEntity>> {
        val res =
            service.getPhoto(10, 10, "latest")
        return if (res.isSuccessful) {
            if (res.body() == null) {
                Log.d("RES", "response body is null")
                Result.success(listOf())
            }
            else {
                Log.d("RES", "response body is not null")
                Log.d("RES", "${PhotoMapper.mapperToResponseEntity(res.body()!!)}")
                Result.success(PhotoMapper.mapperToResponseEntity(res.body()!!))
            }
        }
        else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }

    override suspend fun getRandomPhoto(count: Int): Result<List<NewPhotoEntity>> {
        val res =
            service.getRandomPhoto(count)
        return if (res.isSuccessful) {
            if (res.body() == null) {
                Log.d("RES", "response body is null")
                Result.success(listOf())
            }
            else {
                Log.d("RES", "response body is not null")
                Log.d("RES", "${RandomPhotoMapper.mapperToResponseEntity(res.body()!!)}")
                Result.success(RandomPhotoMapper.mapperToResponseEntity(res.body()!!))
            }
        }
        else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }
}