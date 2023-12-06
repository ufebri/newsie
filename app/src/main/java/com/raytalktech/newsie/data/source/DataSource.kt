package com.raytalktech.newsie.data.source

import androidx.lifecycle.LiveData
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.utils.vo.Resource

interface DataSource {

    fun getAllNewsData(category: String): LiveData<Resource<List<DataEntity>>>

    fun getBreakingNews(): LiveData<List<DataEntity>>

    fun getAllSourceNews(): LiveData<List<DataEntity>>

    fun getDetailSourceNews(sourceName: String): LiveData<List<DataEntity>>

    fun getAllNewsByCategory(category: String): LiveData<Resource<List<DataEntity>>>
}