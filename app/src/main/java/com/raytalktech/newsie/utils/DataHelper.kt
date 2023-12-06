package com.raytalktech.newsie.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DataHelper {
    fun formatDate(inputDate: String): String {
        try {
            // Define input date format
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")

            // Parse the input date
            val date: Date? = inputFormat.parse(inputDate)

            // Define output date format
            val outputFormat = SimpleDateFormat("hh:mm a EEEE, dd MMMM yyyy", Locale.getDefault())

            // Format the date in the desired format
            return outputFormat.format(date ?: inputDate)
        } catch (e: Exception) {
            return inputDate
        }
    }

    fun isCurrentDateUpdated(lastUpdatedDate: String): Boolean {
        // Parse the last updated date
        val lastUpdated =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(lastUpdatedDate)

        // Get the current date and time
        val currentDate = Calendar.getInstance().time

        // Check if the current date is after the last updated date
        return currentDate.after(lastUpdated)
    }

    fun formatDateString(inputDateString: String): String {
        try {
            val inputFormat = SimpleDateFormat("hh:mm a EEEE, dd MMMM yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("EEEE", Locale.getDefault())

            val date = inputFormat.parse(inputDateString)

            return if (date != null) {
                val currentDate = Calendar.getInstance().time

                when {
                    isSameDay(date, currentDate) -> "Today, " + outputFormat.format(date)
                    isSameDay(
                        date,
                        Date(currentDate.time - 24 * 60 * 60 * 1000)
                    ) -> "Yesterday, " + outputFormat.format(date)

                    else -> outputFormat.format(date)
                }
            } else {
                inputDateString
            }
        } catch (e: Exception) {
            return inputDateString
        }
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
}