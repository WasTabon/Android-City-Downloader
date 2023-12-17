package com.example.weatherapplication.networking

import com.example.weatherapplication.model.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    // Get current weather data
    @GET("current.json")
    fun getCurrentWeather(
        @Query("key") key: String = ApiConfig.API_KEY,
        @Query("q") city: String,
        @Query("aqi") aqi: String = "no"
    ): Call<CurrentWeatherResponse>
}
