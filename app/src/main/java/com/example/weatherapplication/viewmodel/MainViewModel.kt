package com.example.weatherapplication.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.weatherapplication.model.CurrentWeatherResponse
import com.example.weatherapplication.networking.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainViewModel() : ViewModel() {

    private val _weatherData = MutableLiveData<CurrentWeatherResponse?>()
    val weatherData: LiveData<CurrentWeatherResponse?> get() = _weatherData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    fun getWeatherData(city: String) {
        _isLoading.value = true
        _isError.value = false

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getCurrentWeather(city = city).execute()
                }
                handleResponse(response)
            } catch (e: Exception) {
                onError(e.message)
                e.printStackTrace()
            }
        }
    }

    private fun handleResponse(response: Response<CurrentWeatherResponse>) {
        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                _weatherData.postValue(responseBody)
            } else {
                onError("Data Processing Error")
            }
        } else {
            onError("Data Processing Error")
        }
        _isLoading.postValue(false)
    }

    private fun onError(inputMessage: String?) {
        val message = inputMessage.takeIf { !it.isNullOrBlank() } ?: "Unknown Error"

        errorMessage = "ERROR: $message some data may not be displayed properly"

        _isError.postValue(true)
        _isLoading.postValue(false)
    }
}
