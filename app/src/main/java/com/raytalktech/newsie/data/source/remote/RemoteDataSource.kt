package com.raytalktech.newsie.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raytalktech.newsie.data.source.remote.response.Articles
import com.raytalktech.newsie.data.source.remote.response.DataResponse
import com.raytalktech.newsie.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteDataSource {

    companion object {
        private val client = ApiConfig.getApiService()
        private const val TAG = "RemoteDataSource"


        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getBreakingNews(category: String): LiveData<ApiResponse<List<Articles>>> {
        val resultItem = MutableLiveData<ApiResponse<List<Articles>>>()
        client.getTopHeadLines(category = category).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        resultItem.value =
                            ApiResponse.success(response.body()?.articles as List<Articles>)
                    } else {
                        if (response.body()?.status == "error") {
                            when (response.code()) {
                                429 -> resultItem.value =
                                    ApiResponse.error(
                                        response.code().toString(),
                                        arrayListOf()
                                    ) //ratelimit

                                else -> resultItem.value =
                                    ApiResponse.error(response.message(), arrayListOf())
                            }
                        } else {
                            resultItem.value = ApiResponse.error(
                                response.message(),
                                response.body()?.articles as List<Articles>
                            )
                        }
                    }
                } else {
                    when (response.code()) {
                        429 -> resultItem.value =
                            ApiResponse.error(
                                response.code().toString(),
                                arrayListOf()
                            ) //ratelimit

                        else -> resultItem.value =
                            ApiResponse.error(response.message(), arrayListOf())
                    }
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                resultItem.value = ApiResponse.error(
                    t.message.toString(),
                    arrayListOf()
                )
            }

        })
        return resultItem
    }

    fun getSearchNews(keyword: String): LiveData<ApiResponse<List<Articles>>> {
        val resultItem = MutableLiveData<ApiResponse<List<Articles>>>()
        client.getSearchingNews(keyword = keyword).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        resultItem.value =
                            ApiResponse.success(response.body()?.articles as List<Articles>)
                    } else {
                        if (response.body()?.status == "error") {
                            when (response.code()) {
                                429 -> resultItem.value =
                                    ApiResponse.error(
                                        response.code().toString(),
                                        arrayListOf()
                                    ) //ratelimit

                                else -> resultItem.value =
                                    ApiResponse.error(response.message(), arrayListOf())
                            }
                        } else {
                            resultItem.value = ApiResponse.error(
                                response.message(),
                                response.body()?.articles as List<Articles>
                            )
                        }
                    }
                } else {
                    when (response.code()) {
                        429 -> resultItem.value =
                            ApiResponse.error(
                                response.code().toString(),
                                arrayListOf()
                            ) //ratelimit

                        else -> resultItem.value =
                            ApiResponse.error(response.message(), arrayListOf())
                    }
                }
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                resultItem.value = ApiResponse.error(
                    t.message.toString(),
                    arrayListOf()
                )
            }

        })
        return resultItem
    }


}