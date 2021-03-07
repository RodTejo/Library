package com.example.library.core.utils

import com.example.library.core.common.AppConstants
import java.text.SimpleDateFormat
import java.util.*

/**
 * Extension function used to transform default utc date obtained from server
 * to a Date object which will be formatted to our desired date format on
 * Date.formatTo extension function
 */
fun String.toDate(dateFormat: String = AppConstants.BookDetail.DateFormat.UTC_FORMAT,
                          timeZone: TimeZone = TimeZone.getTimeZone(AppConstants.BookDetail.DateFormat.UTC_TIMEZONE)): Date? {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

/**
 * Extension function that generates a date string in our given date format from a
 * Date object
 */
fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}