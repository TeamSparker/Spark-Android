package com.spark.android.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.spark.android.R

object SendSparkToast {
    fun showToast(context: Context) {
        Toast(context).apply {
            setGravity(Gravity.TOP, 0, 20.toPx())
            duration = Toast.LENGTH_LONG
            view =
                LayoutInflater.from(context).inflate(R.layout.toast_habit_send_spark_complete, null)
            show()
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}