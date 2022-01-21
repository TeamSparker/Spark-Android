package com.spark.android.ui.timer

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer.OnChronometerTickListener
import androidx.activity.viewModels
import com.spark.android.R
import com.spark.android.databinding.ActivityTimerStartBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.certify.CertifyActivity
import com.spark.android.ui.certify.CertifyMode
import com.spark.android.ui.timer.viewmodel.TimerStartViewModel
import com.spark.android.util.DialogUtil
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite


class TimerStartActivity : BaseActivity<ActivityTimerStartBinding>(R.layout.activity_timer_start) {
    private val timerStartViewModel by viewModels<TimerStartViewModel>()
    var pauseTime = 0L
    var roomName: String? = null
    var roomId: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.timerStartViewModel = timerStartViewModel

        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
        val timerRecord = intent.getStringExtra("timerRecord")
        initFormatChange(timerRecord)
        initClickEvent()
        val myVisible = intent.getIntExtra("myVisible", View.INVISIBLE)
        binding.btnTimerStop.visibility = myVisible
        binding.btnTimerPlay.visibility = myVisible
        val myInvisible = intent.getIntExtra("myInvisible", View.VISIBLE)
        binding.btnTimerStartBottom.visibility = myInvisible
        roomName = intent.getStringExtra("roomName")
        binding.tvTimerRoomName.text = roomName
        roomId = intent.getIntExtra("roomId", -1)

        //binding.chronometerTimer.base = SystemClock.elapsedRealtime() - (nr_of_min * 60000)

        //initTimerStateObserver()
    }

//    private fun initTimerStateObserver() {
//        timerStartViewModel.timerState.observe(this) {
//            when (it) {
//                TIMER_RESET -> {
//                    binding.chronometerTimer.base = SystemClock.elapsedRealtime()
//                }
//                TIMER_RUN -> {
//                    binding.chronometerTimer.base = SystemClock.elapsedRealtime()
//                    binding.chronometerTimer.start()
//                }
//                TIMER_PAUSE -> {
//                    binding.chronometerTimer.stop()
//                }
//            }
//        }
//    }

    private fun initClickEvent() {
        binding.btnTimerStartBottom.setOnClickListener {
            timerStartViewModel.initTimerRun()
            binding.btnTimerStartBottom.visibility = View.INVISIBLE
            binding.btnTimerPause.visibility = View.VISIBLE
            binding.btnTimerStop.visibility = View.VISIBLE

            binding.chronometerTimer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometerTimer.start()
        }

        binding.btnTimerStop.setOnClickListener {

            DialogUtil(DialogUtil.STOP_TIMER) {
                timerStartViewModel.initTimerReset()
                binding.btnTimerStop.visibility = View.INVISIBLE
                binding.btnTimerPause.visibility = View.INVISIBLE
                binding.btnTimerPlay.visibility = View.INVISIBLE
                binding.btnTimerStartBottom.visibility = View.VISIBLE
                pauseTime = 0L
                binding.chronometerTimer.base = SystemClock.elapsedRealtime()
                initFormatChange(null)
                binding.chronometerTimer.stop()
            }.show(supportFragmentManager, this.javaClass.name)

        }

        binding.btnTimerPlay.setOnClickListener {
            timerStartViewModel.initTimerRun()
            binding.btnTimerPlay.visibility = View.INVISIBLE
            binding.btnTimerPause.visibility = View.VISIBLE

            binding.chronometerTimer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometerTimer.start()
        }

        binding.btnTimerPause.setOnClickListener {
            timerStartViewModel.initTimerPause()
            binding.btnTimerPause.visibility = View.INVISIBLE
            binding.btnTimerPlay.visibility = View.VISIBLE

            pauseTime = binding.chronometerTimer.base - SystemClock.elapsedRealtime()
            binding.chronometerTimer.stop()

        }

        binding.btnTimerQuit.setOnClickListener {
            // TimerStartActivity에서 x버튼을 누를 시 -> finish() -> HabitTodayBottomSheet로 돌아감

            binding.btnTimerQuit.setOnClickListener {
                DialogUtil(DialogUtil.STOP_CERTIFY_TIMER) {
                    finish()
                }.show(supportFragmentManager, this.javaClass.name)
            }
        }

        binding.btnTimerNextStepBottom.setOnClickListener {
            // TimerStartActivity에서 다음 단계로 버튼 누를시 ->
            // 1. 타이머 시간값 : binding.chronometerTimer.text
            // 2. 룸 아이디 값
            // 가지고 사진 인증 액티비티로 넘겨야 함

            val intent = Intent(this, CertifyActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                putExtra("timerRecord", binding.chronometerTimer.text)
                putExtra("roomName", roomName)
                putExtra("roomId", roomId)
                putExtra("certifyMode", CertifyMode.NORMAL_READY_MODE)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun initFormatChange(timerRecord: String?) {
        val chrono = binding.chronometerTimer
//        chrono.base = SystemClock.elapsedRealtime()
//        chrono.text = "00:00:00"

        if (timerRecord.isNullOrBlank()) {
            chrono.base = SystemClock.elapsedRealtime()
            chrono.text = "00:00:00"
        } else {
            val timeArray = requireNotNull(timerRecord).split(":")
            val hour = timeArray[0].toLong()
            val min = timeArray[1].toLong()
            val sec = timeArray[2].toLong()
            pauseTime = (hour * 3600L + min * 60L + sec) * (-1000L)
            //chrono.base = SystemClock.elapsedRealtime() - pauseTime*1000L
            chrono.text = timerRecord
        }


        chrono.onChronometerTickListener =
            OnChronometerTickListener { chronometer ->
                val time = SystemClock.elapsedRealtime() - chronometer.base
                val h = (time / 3600000).toInt()
                val m = (time - h * 3600000).toInt() / 60000
                val s = (time - h * 3600000 - m * 60000).toInt() / 1000
                val t =
                    (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s
                chronometer.text = t
            }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        DialogUtil(DialogUtil.STOP_CERTIFY_TIMER) {
            finish()
        }.show(supportFragmentManager, this.javaClass.name)
    }
}
