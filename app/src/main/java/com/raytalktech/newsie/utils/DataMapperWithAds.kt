package com.raytalktech.newsie.utils

import com.raytalktech.newsie.data.source.local.entity.DataEntity

object DataMapperWithAds {
    fun populateListDataWithAds(mDataList: List<DataEntity>): List<DataEntity> {
        val dataList = mutableListOf<DataEntity>()

        for (index in mDataList.indices) {
            dataList.add(mDataList[index])

            if (index != 0 && index % 10 == 0) {
                dataList.add(setDataAdsMeal())
            }
        }

        return dataList
    }

    private fun setDataAdsMeal(): DataEntity {
        val index: IntRange = 1..99
        return DataEntity(
            id = "$index-ads",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            viewType = 2,
            faviconUrl = ""
        )
    }


}