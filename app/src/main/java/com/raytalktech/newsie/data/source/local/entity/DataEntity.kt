package com.raytalktech.newsie.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "DataEntity")
data class DataEntity(

    @PrimaryKey @NonNull @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "sourceName") val name: String,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "urlToImage") val urlToImage: String?,
    @ColumnInfo(name = "publishedAt") val publishedAt: String,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "faviconUrl") val faviconUrl: String?,
    @ColumnInfo(name = "viewType") var viewType: Int = 1

) : Parcelable
