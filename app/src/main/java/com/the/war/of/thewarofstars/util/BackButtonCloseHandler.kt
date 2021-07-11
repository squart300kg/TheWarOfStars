package com.the.war.of.thewarofstars.util

import android.app.Activity
import android.widget.Toast
import com.the.war.of.thewarofstars.R

class BackButtonCloseHandler(private var activity: Activity) {
    private var backButtonPressedTime: Long = 0
    private var toast: Toast? = null

    fun onBackPressed() {
        if (System.currentTimeMillis() > backButtonPressedTime + intervalTimeMillis) {
            backButtonPressedTime = System.currentTimeMillis()
            toast = Toast.makeText(activity, R.string.back_button_close_guide, Toast.LENGTH_SHORT)
            toast?.show()
            return
        } else {
            activity.finish()
            toast?.cancel()
        }
    }

    companion object {
        private const val intervalTimeMillis = 2000
    }
}