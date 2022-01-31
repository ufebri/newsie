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
        client.getBreakingNews(category = category).enqueue(object : Callback<DataResponse> {
            override fun onResponse(call: Call<DataResponse>, response: Response<DataResponse>) {
                resultItem.value =
                    ApiResponse.success(response.body()?.articles as List<Articles>)
            }

            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
        return resultItem
    }


}