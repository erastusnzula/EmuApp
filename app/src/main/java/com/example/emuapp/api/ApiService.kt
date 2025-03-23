package com.example.emuapp.api

import com.example.emuapp.data.ApiItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/products")
    fun getData(
        @Query("limit") limit: Int):Call<ApiItems>

    @GET("/products/category/laptops")
    fun getDataByCategory(
        @Query("limit") limit: Int):Call<ApiItems>
}