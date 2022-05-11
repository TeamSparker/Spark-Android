package com.teamsparker.android.util

import android.content.Context
import android.content.res.Resources
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ToastHabitSendSparkCompleteBinding

object SendSparkToast {
    private var toast: Toast? = null

    fun showToast(context: Context, nickname: String) {
        toast = Toast(context)
        val inflater = LayoutInflater.from(context)
        val binding: ToastHabitSendSparkCompleteBinding =
            DataBindingUtil.inflate(inflater, R.layout.toast_habit_send_spark_complete, null, false)
        binding.nickname = nickname
        if (toast != null) {
            requireNotNull(toast).apply {
                setGravity(Gravity.TOP, 0, 20.toPx())
                duration = Toast.LENGTH_SHORT
                view = binding.root
                show()
            }
        }
    }

    fun cancelToast() {
        if (toast != null) {
            requireNotNull(toast).cancel()
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
