package com.example.emuapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance{
    private const val BASE_URL = "https://dummyjson.com"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}


object ApiClient {
    val apiService: ApiService by lazy {
        RetrofitInstance.retrofit.create(ApiService::class.java)
    }
}