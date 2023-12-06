package com.raytalktech.newsie.utils

import org.jsoup.Jsoup
import java.net.HttpURLConnection
import java.net.URL

object FaviconHelper {

    fun getFaviconUrl(url: String): String? {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val htmlContent = connection.inputStream.bufferedReader().use { it.readText() }

            // Use regular expression to find favicon URL in HTML content
            val faviconRegex = """<link.*?rel=["']?icon["']?.*?href=["'](.*?)["'].*?>""".toRegex(RegexOption.IGNORE_CASE)
            val matchResult = faviconRegex.find(htmlContent)

            // Extract the URL from the match result
            return matchResult?.groups?.get(1)?.value ?: getFaviconUrls(url)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun getFaviconUrls(url: String): String? {
        try {
            val htmlContent = URL(url).readText()
            val document = Jsoup.parse(htmlContent)

            // Attempt to find the favicon link tag
            val faviconElement = document.select("link[rel~=(?i)icon]").first()

            // If found, extract the href attribute
            return faviconElement?.attr("href")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getBaseUrl(urlString: String): String {
        val endIndex = urlString.indexOf("/", urlString.indexOf("://") + 3)
        return if (endIndex != -1) urlString.substring(0, endIndex) else urlString
    }
}
