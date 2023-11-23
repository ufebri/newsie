package com.raytalktech.newsie.network

import com.raytalktech.newsie.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor =
                HttpLoggingInterceptor()

            if (BuildConfig.DEBUG)
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            else
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}