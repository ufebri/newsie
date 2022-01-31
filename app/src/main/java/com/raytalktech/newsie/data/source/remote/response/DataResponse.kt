package com.raytalktech.newsie.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataResponse(

    @SerializedName("status") var status: String,
    @SerializedName("totalResults") var totalResults: Int,
    @SerializedName("articles") var articles: List<Articles>

) : Parcelable

@Parcelize
data class Articles(

    @SerializedName("source") var source: Source,
    @SerializedName("author") var author: String,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("url") var url: String,
    @SerializedName("urlToImage") var urlToImage: String,
    @SerializedName("publishedAt") var publishedAt: String,
    @SerializedName("content") var content: String
) : Parcelable

@Parcelize
data class Source(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String

) : Parcelable
