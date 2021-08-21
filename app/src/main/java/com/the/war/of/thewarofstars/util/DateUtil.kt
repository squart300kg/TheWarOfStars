package com.the.war.of.thewarofstars.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val outputPattern = "yyyy-MM-dd'T'HH:mm:ss"
    private const val outputPattern2 = "yyyy년 MM월 dd일 HH시 mm분"
    fun getCurrentDateString(): String {

        val currentDateFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        val currentDate = Date(System.currentTimeMillis())
        val currentDatestring = currentDateFormat.format(currentDate)

        return currentDatestring
    }

    fun getCurrentDateForPayComplete(): String {

        val currentDateFormat = SimpleDateFormat(outputPattern2, Locale.getDefault())
        val currentDate = Date(System.currentTimeMillis())
        val currentDatestring = currentDateFormat.format(currentDate)

        return currentDatestring
    }
}