package com.raytalktech.newsie.network

import com.raytalktech.newsie.BuildConfig
import com.raytalktech.newsie.data.source.remote.response.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getBreakingNews(
        @Query("apiKey") apiKey: String = BuildConfig.APIKEY,
        @Query("country") country: String = "id",
        @Query("pageSize") pageSize: String = "100",
        @Query("category") category: String
    ): Call<DataResponse>

}