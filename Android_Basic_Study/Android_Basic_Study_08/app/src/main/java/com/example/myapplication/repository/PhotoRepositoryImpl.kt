package com.example.myapplication.repository

import android.nfc.Tag
import android.util.Log
import com.example.myapplication.entity.NewPhotoEntity
import com.example.myapplication.data.RetrofitClient
import com.example.myapplication.data.services.UnsplashService
import com.example.myapplication.entity.DetailPhotoEntity
import org.json.JSONObject

class PhotoRepositoryImpl : PhotoRepository {
    private val service = RetrofitClient.getInstance().create(UnsplashService::class.java)
    override suspend fun getPhoto(
        page: Int,
        per_page: Int,
        order_by: String
    ): Result<List<NewPhotoEntity>> {
        val res =
            service.getPhoto(page, per_page, order_by)
        return if (res.isSuccessful) {
            if (res.body() == null) {
                Result.success(listOf())
            }
            else {
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
                Result.success(listOf())
            }
            else {
                Log.d("RES", "${RandomPhotoMapper.mapperToResponseEntity(res.body()!!)}")
                Result.success(RandomPhotoMapper.mapperToResponseEntity(res.body()!!))
            }
        }
        else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }

    override suspend fun getPhotoDetail(
        id: String
        ): Result<DetailPhotoEntity> {
        val res =
            service.getPhotoDetail(id)
        return if (res.isSuccessful) {
            if (res.body() == null) {
                val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
                Result.failure(java.lang.Exception(errorMsg))
            }
            else {
                Result.success(DetailPhotoMapper.mapperToResponseEntity(res.body()!!))
            }
        }
        else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }
}