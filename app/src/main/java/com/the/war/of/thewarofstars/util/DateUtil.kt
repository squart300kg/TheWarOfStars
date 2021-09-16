package com.the.war.of.thewarofstars.util

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val outputPattern = "yyyy-MM-dd'T'HH:mm:ss"
    private const val outputPattern2 = "yyyy년 MM월 dd일 HH시 mm분"
    private const val outputForPay = "yyyyMMddHHmm"
    fun getCurrentTimeMillisForPay(): Long {

        val currentDateFormat = SimpleDateFormat(outputForPay, Locale.getDefault())
        val currentDate = System.currentTimeMillis()
//        val currentDatestring = currentDateFormat.format(currentDate)

        return currentDate
    }

    fun getCurrentDateForPayComplete(): String {

        val currentDateFormat = SimpleDateFormat(outputPattern2, Locale.getDefault())
        val currentDate = Date(System.currentTimeMillis())
        val currentDatestring = currentDateFormat.format(currentDate)

        return currentDatestring
    }

    fun getDateFromTimeMillis(timeMillis : Long?) : String {
        val currentDateFormat = SimpleDateFormat(outputPattern2, Locale.getDefault())

        Calendar.getInstance().apply {
            if (timeMillis != null) {
                timeInMillis = timeMillis
            }
            Log.i("getDateFromTimeMillis", "timeMillis : $timeMillis, result : ${currentDateFormat.format(this.time)}")
            return currentDateFormat.format(this.time)
        }

//        val currentDate = timeMillis?.let { Date(it) }
//        val currentDatestring = currentDateFormat.format(currentDate)
//
//        return currentDatestring
    }
}