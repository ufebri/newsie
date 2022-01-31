package com.raytalktech.newsie.data.source.local

import androidx.lifecycle.LiveData
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.data.source.local.room.AppDao

class LocalDataSource(private val mAppDao: AppDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(mAppDao: AppDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE = LocalDataSource(mAppDao)
            }
            return INSTANCE as LocalDataSource
        }
    }

    fun getAllDataNews(): LiveData<List<DataEntity>> = mAppDao.getAllDataNews()

    fun insertAllNewsData(mData: List<DataEntity>) = mAppDao.insertNewsData(mData)

    fun getBreakingNews(): LiveData<List<DataEntity>> = mAppDao.getBreakingNews()
}