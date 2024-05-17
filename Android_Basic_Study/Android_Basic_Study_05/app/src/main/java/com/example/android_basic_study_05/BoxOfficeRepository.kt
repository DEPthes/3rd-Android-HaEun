package com.example.android_basic_study_05

// 여기는 호출
interface BoxOfficeRepository {
    suspend fun getMovie(
        key: String,
        targetDT: String
    ) : List<DailyBoxOfficeList>
}