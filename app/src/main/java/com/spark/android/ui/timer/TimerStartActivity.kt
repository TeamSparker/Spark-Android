package com.spark.android.ui.timer

import android.os.Bundle
import com.spark.android.databinding.ActivityTimerStartBinding
import com.spark.android.ui.base.BaseActivity
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.os.SystemClock
import android.view.View


class TimerStartActivity :
    BaseActivity<ActivityTimerStartBinding>(com.spark.android.R.layout.activity_timer_start) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTimerFormatChange()



    }

    private fun initTimerFormatChange() {
        val timeElapsed =
            findViewById<View>(com.spark.android.R.id.chronometer_timer_stopwatch) as Chronometer
        timeElapsed.onChronometerTickListener =
            OnChronometerTickListener { cArg ->
                val time: Long = SystemClock.elapsedRealtime() - cArg.base
                val h = (time / 3600000).toInt()
                val m = (time - h * 3600000).toInt() / 60000
                val s = (time - h * 3600000 - m * 60000).toInt() / 1000
                val hh = if (h < 10) "0$h" else h.toString() + ""
                val mm = if (m < 10) "0$m" else m.toString() + ""
                val ss = if (s < 10) "0$s" else s.toString() + ""
                cArg.text = "$hh:$mm:$ss"
            }
        timeElapsed.base = SystemClock.elapsedRealtime()
        timeElapsed.start()
    }
}