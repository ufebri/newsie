package com.raytalktech.newsie.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raytalktech.newsie.data.source.DataRepository
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.utils.vo.Resource

class HomeViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getAllNewsData(category: String): LiveData<Resource<List<DataEntity>>> =
        dataRepository.getAllNewsData(category)

    fun breakingNews(): LiveData<List<DataEntity>> = dataRepository.getBreakingNews()

    fun getAllSourceNewsData(): LiveData<List<DataEntity>> = dataRepository.getAllSourceNews()

    fun getDetailSourceNews(sourceName: String): LiveData<List<DataEntity>> =
        dataRepository.getDetailSourceNews(sourceName)

    fun getAllNewsByCategory(category: String): LiveData<Resource<List<DataEntity>>> =
        dataRepository.getAllNewsByCategory(category)
}