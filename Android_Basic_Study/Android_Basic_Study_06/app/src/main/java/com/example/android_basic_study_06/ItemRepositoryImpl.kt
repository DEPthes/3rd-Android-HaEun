package com.example.android_basic_study_06

import org.json.JSONObject

class ItemRepositoryImpl : ItemRepository {
    private val service = RetrofitClient.getInstance().create(Service::class.java)
    override suspend fun getItem(searchItem: String): Result<List<ItemEntity>> {
        val response = service.getItem(searchItem)
        return if (response.isSuccessful) {
            Result.success(ItemMapping.mapperToResponseEntity(response.body()!!))
        } else {
            val errorMsg = JSONObject(response.errorBody()!!.string()).getString("msg")
            Result.failure(errorMsg)
        }
    }
}