package com.example.android_basic_study_05

import com.example.android_basic_study_05.RetrofitClient.KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class BoxOfficeRepositoryImpl : BoxOfficeRepository {
    private var retrofit: Retrofit = RetrofitClient.getInstnace()
    private var service: TestInterface = retrofit.create(TestInterface::class.java)

    override suspend fun getMovie(key: String, targetDT: String): List<DailyBoxOfficeList> {
        return withContext(Dispatchers.IO) {
            val call = service.getMovie(KEY, targetDT).execute()
            if (call.isSuccessful) {
                val response = call.body()
                response?.boxOfficeResult?.dailyBoxOfficeList ?: emptyList()
            } else {
                emptyList()
            }
        }
    }
}