package com.teamsparker.android.ui.timer

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer.OnChronometerTickListener
import androidx.activity.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.ActivityTimerStartBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.certify.CertifyActivity
import com.teamsparker.android.ui.certify.CertifyMode
import com.teamsparker.android.ui.timer.viewmodel.TimerStartViewModel
import com.teamsparker.android.ui.timer.viewmodel.TimerStartViewModel.Companion.TIMER_PAUSE
import com.teamsparker.android.ui.timer.viewmodel.TimerStartViewModel.Companion.TIMER_RESET
import com.teamsparker.android.ui.timer.viewmodel.TimerStartViewModel.Companion.TIMER_RUN
import com.teamsparker.android.util.DialogUtil
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.SCREEN_STOPWATCH
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite

class TimerStartActivity : BaseActivity<ActivityTimerStartBinding>(R.layout.activity_timer_start) {
    private val timerStartViewModel by viewModels<TimerStartViewModel>()
    var pauseTime = 0L
    var roomName: String? = null
    var roomId: Int? = -1
    var nickname = ""
    var profileImgUrl = ""
    var comeBackTimerState = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseLogUtil.logScreenEvent(this.javaClass.name.split(".").last(), SCREEN_STOPWATCH)
        binding.timerStartViewModel = timerStartViewModel

        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
        val timerRecord = intent.getStringExtra("timerRecord")
        initFormatChange(timerRecord)
        initClickEvent()
        initVisibleStopAndPlayBtn(comeBackTimerState)

        roomName = intent.getStringExtra("roomName")
        binding.tvTimerRoomName.text = roomName
        roomId = intent.getIntExtra("roomId", -1)
        profileImgUrl = intent.getStringExtra("profileImgUrl") ?: ""
        nickname = intent.getStringExtra("nickname") ?: ""

    }

    private fun initVisibleStopAndPlayBtn(comeBackTimerState: Int) {
        val comeBackTimerState = intent.getIntExtra("comeBackTimerState", TIMER_RESET)
        timerStartViewModel.setState(comeBackTimerState)
    }

    private fun initClickEvent() {
        binding.btnTimerStartBottom.setOnClickListener {
            timerStartViewModel.setState(TIMER_RUN)

            binding.chronometerTimer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometerTimer.start()
        }

        binding.btnTimerStop.setOnClickListener {

            DialogUtil(DialogUtil.STOP_TIMER) {
                timerStartViewModel.setState(TIMER_RESET)

                pauseTime = 0L
                binding.chronometerTimer.base = SystemClock.elapsedRealtime()
                initFormatChange(null)
                binding.chronometerTimer.stop()
            }.show(supportFragmentManager, this.javaClass.name)

        }

        binding.btnTimerPlay.setOnClickListener {
            timerStartViewModel.setState(TIMER_RUN)

            binding.chronometerTimer.base = SystemClock.elapsedRealtime() + pauseTime
            binding.chronometerTimer.start()
        }

        binding.btnTimerPause.setOnClickListener {
            timerStartViewModel.setState(TIMER_PAUSE)

            pauseTime = binding.chronometerTimer.base - SystemClock.elapsedRealtime()
            binding.chronometerTimer.stop()
        }

        binding.btnTimerQuit.setOnClickListener {

            binding.btnTimerQuit.setOnClickListener {
                DialogUtil(DialogUtil.STOP_CERTIFY_TIMER) {
                    finish()
                }.show(supportFragmentManager, this.javaClass.name)
            }
        }

        binding.btnTimerNextStepBottom.setOnClickListener {

            val intent = Intent(this, CertifyActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                putExtra("timerRecord", binding.chronometerTimer.text)
                putExtra("roomName", roomName)
                putExtra("roomId", roomId)
                putExtra("certifyMode", CertifyMode.NORMAL_READY_MODE)
                putExtra("profileImgUrl", profileImgUrl)
                putExtra("nickname", nickname)
                putExtra("leftDay", intent.getIntExtra("leftDay", -1))
            }
            startActivity(intent)
            finish()
        }
    }

    private fun initFormatChange(timerRecord: String?) {
        val chrono = binding.chronometerTimer

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
