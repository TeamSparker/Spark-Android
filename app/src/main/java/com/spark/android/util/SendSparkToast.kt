package com.spark.android.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.spark.android.R
import com.spark.android.databinding.ToastHabitSendSparkCompleteBinding

object SendSparkToast {
    fun showToast(context: Context, nickname: String) {
        val inflater = LayoutInflater.from(context)
        val binding: ToastHabitSendSparkCompleteBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_habit_send_spark_complete, null, false)

        binding.nickname = nickname

        Toast(context).apply {
            setGravity(Gravity.TOP, 0, 20.toPx())
            duration = Toast.LENGTH_LONG
            view = binding.root
            show()
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}