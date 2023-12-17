package com.raytalktech.newsie.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.raytalktech.newsie.data.source.local.entity.DataEntity

@Dao
interface AppDao {

    @Query("SELECT * FROM DataEntity")
    fun getAllDataNews(): LiveData<List<DataEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsData(mData: List<DataEntity>)

    @Query("SELECT * FROM DataEntity WHERE category = :selectedCategory ORDER BY id ASC LIMIT 10")
    fun getNewsByCategories(selectedCategory: String): LiveData<List<DataEntity>>

    @Query("SELECT DISTINCT * FROM dataentity GROUP BY category ORDER BY id DESC")
    fun getBreakingNews(): LiveData<List<DataEntity>>

    @Query("SELECT DISTINCT * FROM dataentity GROUP BY sourceName ORDER BY faviconUrl DESC")
    fun getAllSource(): LiveData<List<DataEntity>>

    @Query("SELECT * FROM DataEntity WHERE sourceName = :sourceName ORDER BY publishedAt DESC")
    fun getAllSourceDetail(sourceName: String): LiveData<List<DataEntity>>

    @Query("SELECT * FROM dataentity WHERE title = :keyword ORDER BY publishedAt DESC")
    fun getAllNewsByKeyword(keyword: String): LiveData<List<DataEntity>>
}