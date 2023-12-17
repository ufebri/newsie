package com.raytalktech.newsie.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raytalktech.newsie.data.source.DataRepository
import com.raytalktech.newsie.data.source.local.entity.DataEntity
import com.raytalktech.newsie.utils.vo.Resource

class SearchViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getSearchListData(keyword: String): LiveData<Resource<List<DataEntity>>> =
        dataRepository.getSearchNewsData(keyword)
}