package com.example.android_basic_study_05

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var instance : Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()
    private var baseUrl = " http://www.kobis.or.kr"

    const val URL_MOVIE = "/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json"
    const val KEY = "0c53b6a11207950675117cd147bf8245"

    var okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor())

    fun getInstnace() : Retrofit {
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient.build())
                .build()
        }
        return instance!!
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            try {
                val jsonObject = JSONObject(message).optJSONObject("boxOfficeResult")
                    ?.optJSONArray("dailyBoxOfficeList")
                for (i in 0 until 5) {
                    val movieObject = jsonObject!!.optJSONObject(i)
                    val rank = movieObject.optString("rank")
                    val movieTitle = movieObject.optString("movieNm")
                    val openDate = movieObject.optString("openDt")
                    val audience = movieObject.optString("audiAcc")
                    Log.d("Result", "rank: $rank, movie title: $movieTitle, " +
                            "open date: $openDate, audience: $audience")
                }
            } catch (e: Exception) {
                Log.d("Result", message)
            }
        }
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}