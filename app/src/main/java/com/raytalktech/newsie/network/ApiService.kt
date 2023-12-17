package com.raytalktech.newsie.network

import com.raytalktech.newsie.BuildConfig
import com.raytalktech.newsie.data.source.remote.response.DataResponse
import com.raytalktech.newsie.utils.DataHelper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    fun getTopHeadLines(
        @Query("apiKey") apiKey: String = BuildConfig.APIKEY,
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: String = "20",
        @Query("category") category: String
    ): Call<DataResponse>

    @GET("everything")
    fun getSearchingNews(
        @Query("apiKey") apiKey: String = BuildConfig.APIKEY,
        @Query("q") keyword: String,
        @Query("sortBy") sortBy: String = "popularity",
        @Query("pageSize") pageSize: String = "8",
        @Query("from") fromDate: String = DataHelper.getCurrentDate("yyyy-MM-dd"),
        @Query("to") toDate: String = DataHelper.getDateNDaysAgo(30)
    ): Call<DataResponse>
}