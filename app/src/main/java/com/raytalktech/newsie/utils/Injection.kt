package com.raytalktech.newsie.utils

import android.content.Context
import com.raytalktech.newsie.data.source.DataRepository
import com.raytalktech.newsie.data.source.local.LocalDataSource
import com.raytalktech.newsie.data.source.local.room.AppDatabase
import com.raytalktech.newsie.data.source.remote.RemoteDataSource

object Injection {
    fun provideRepository(context: Context): DataRepository {
        val database = AppDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.jetMovieDao())
        val appExecutors = AppExecutors()
        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}