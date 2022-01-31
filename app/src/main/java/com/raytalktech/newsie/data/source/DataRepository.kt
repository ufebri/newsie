package com.raytalktech.newsie.data.source

import androidx.lifecycle.LiveData
import com.raytalktech.newsie.data.NetworkBoundResource
import com.raytalktech.newsie.data.source.local.LocalDataSource
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.data.source.remote.ApiResponse
import com.raytalktech.newsie.data.source.remote.RemoteDataSource
import com.raytalktech.newsie.data.source.remote.response.Articles
import com.raytalktech.newsie.utils.AppExecutors
import com.raytalktech.newsie.utils.vo.Resource

class DataRepository private constructor(
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val remoteDataSource: RemoteDataSource
) :
    DataSource {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(
                    localDataSource,
                    appExecutors,
                    remoteData
                )
            }
    }

    override fun getAllNewsData(category: String): LiveData<Resource<List<DataEntity>>> {
        return object : NetworkBoundResource<List<DataEntity>, List<Articles>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<DataEntity>> =
                localDataSource.getAllDataNews()

            //TODO: Make this fetch when new data date available
            override fun shouldFetch(data: List<DataEntity>?): Boolean {
                return data == null || data.isEmpty()
            }


            override fun createCall(): LiveData<ApiResponse<List<Articles>>> =
                remoteDataSource.getBreakingNews(category)

            override fun saveCallResult(data: List<Articles>) {
                val listBreakingNews = ArrayList<DataEntity>()
                for (response in data) {
                    with(response) {
                        val articles = DataEntity(
                            publishedAt,
                            source.name,
                            author,
                            title,
                            description,
                            url,
                            urlToImage,
                            publishedAt,
                            content,
                            category
                        )
                        listBreakingNews.add(articles)
                    }
                }
                localDataSource.insertAllNewsData(listBreakingNews)
            }
        }.asLiveData()
    }

    override fun getBreakingNews(): LiveData<List<DataEntity>> {
        return localDataSource.getBreakingNews()
    }
}