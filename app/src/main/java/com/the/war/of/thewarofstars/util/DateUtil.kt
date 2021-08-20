package com.the.war.of.thewarofstars.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val outputPattern = "yyyy-MM-dd'T'HH:mm:ss"
    fun getCurrentDateString(): String {

        val currentDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        val currentDate = Date(System.currentTimeMillis())
        val currentDatestring = currentDateFormat.format(currentDate)

        return currentDatestring
    }
}