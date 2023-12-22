package com.raytalktech.newsie.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class ContentOnboarding(
    val contentID: String,
    val contentName: String,
    val contentType: String = "country"
) : Parcelable

val topicList = listOf(
    ContentOnboarding("1", "Business", contentType = "topic"),
    ContentOnboarding("2", "Entertainment", contentType = "topic"),
    ContentOnboarding("3", "General", contentType = "topic"),
    ContentOnboarding("4", "Health", contentType = "topic"),
    ContentOnboarding("5", "Science", contentType = "topic"),
    ContentOnboarding("6", "Sports", contentType = "topic"),
    ContentOnboarding("7", "Technology", contentType = "topic")
)

val countryList = listOf(
    ContentOnboarding("ae", "United Arab Emirates"),
    ContentOnboarding("ar", "Argentina"),
    ContentOnboarding("at", "Austria"),
    ContentOnboarding("au", "Australia"),
    ContentOnboarding("be", "Belgium"),
    ContentOnboarding("bg", "Bulgaria"),
    ContentOnboarding("br", "Brazil"),
    ContentOnboarding("ca", "Canada"),
    ContentOnboarding("ch", "Switzerland"),
    ContentOnboarding("cn", "China"),
    ContentOnboarding("co", "Colombia"),
    ContentOnboarding("cr", "Costa Rica"),
    ContentOnboarding("cz", "Czech Republic"),
    ContentOnboarding("de", "Germany"),
    ContentOnboarding("eg", "Egypt"),
    ContentOnboarding("fr", "France"),
    ContentOnboarding("gb", "United Kingdom"),
    ContentOnboarding("gr", "Greece"),
    ContentOnboarding("hk", "Hong Kong"),
    ContentOnboarding("hu", "Hungary"),
    ContentOnboarding("id", "Indonesia"),
    ContentOnboarding("ie", "Ireland"),
    ContentOnboarding("il", "Israel"),
    ContentOnboarding("in", "India"),
    ContentOnboarding("it", "Italy"),
    ContentOnboarding("jp", "Japan"),
    ContentOnboarding("kr", "South Korea"),
    ContentOnboarding("lt", "Lithuania"),
    ContentOnboarding("lv", "Latvia"),
    ContentOnboarding("ma", "Morocco"),
    ContentOnboarding("mx", "Mexico"),
    ContentOnboarding("my", "Malaysia"),
    ContentOnboarding("ng", "Nigeria"),
    ContentOnboarding("nl", "Netherlands"),
    ContentOnboarding("nz", "New Zealand"),
    ContentOnboarding("ph", "Philippines"),
    ContentOnboarding("pl", "Poland"),
    ContentOnboarding("pt", "Portugal"),
    ContentOnboarding("ro", "Romania"),
    ContentOnboarding("rs", "Serbia"),
    ContentOnboarding("ru", "Russia"),
    ContentOnboarding("sa", "Saudi Arabia"),
    ContentOnboarding("se", "Sweden"),
    ContentOnboarding("sg", "Singapore"),
    ContentOnboarding("sk", "Slovakia"),
    ContentOnboarding("th", "Thailand"),
    ContentOnboarding("tr", "Turkey"),
    ContentOnboarding("tw", "Taiwan"),
    ContentOnboarding("ua", "Ukraine"),
    ContentOnboarding("us", "United States"),
    ContentOnboarding("ve", "Venezuela"),
    ContentOnboarding("za", "South Africa")
)