package com.teamsparker.android.ui.habit

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.data.remote.LocalPreferences
import com.teamsparker.android.databinding.ActivityHabitBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.habit.adapter.HabitRecyclerViewAdapter
import com.teamsparker.android.ui.habit.userguide.UserGuideFragmentDialog
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.SCREEN_HABIT_ROOM
import com.teamsparker.android.util.ext.setOnSingleClickListener
import com.teamsparker.android.util.initStatusBarColor
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class HabitActivity : BaseActivity<ActivityHabitBinding>(R.layout.activity_habit) {
    private lateinit var habitRecyclerViewAdapter: HabitRecyclerViewAdapter

    private val habitViewModel by viewModels<HabitViewModel>()
    private var roomId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseLogUtil.logScreenEvent(this.javaClass.name.split(".").last(), SCREEN_HABIT_ROOM)
        binding.habitViewModel = habitViewModel
        initStatusBarColor(R.color.spark_black)
        initRoomId()
        initRVAdapter()
        initHabitInfoObserver()
        initHabitRecordsObserver()
        initRefreshSuccessObserver()
        initExitSuccessObserver()
        setSwipeRefreshLayout()
        initHabitBackBtnClickListener()
        initHabitMoreBtnClickListener()
        initHabitTodayBtnClickListener()
        checkUserGuideDialog()
        initGroupTeamLifeClickListener()
    }

    private fun initRoomId() {
        roomId = intent.getIntExtra("roomId", -1)
    }

    private fun initHabitInfoObserver() {
        habitViewModel.habitInfo.observe(this) {
            habitRecyclerViewAdapter.response = it
            binding.habitViewModel = habitViewModel
//            1.1.0에서 삭제 다른기능으로 대체
//            initHabitLifeLessDialog()
        }
    }

    private fun initRefreshSuccessObserver() {
        habitViewModel.refreshSuccess.observe(this) {
            if (habitViewModel.refreshSuccess.value == true) {
                refreshData()
                habitViewModel.initRefreshSuccess(false)
            }
        }
    }

    private fun initExitSuccessObserver() {
        habitViewModel.exitSuccess.observe(this) {
            if (habitViewModel.exitSuccess.value == true) {
                var toastMessage = habitViewModel.habitInfo.value!!.roomName
                if (toastMessage.length > 8) {
                    toastMessage = toastMessage.chunked(8)[0] + "..."
                }
                habitViewModel.initExitSuccess(false)
                LocalPreferences.setExitHabitRoomHomeToastMessage("‘$toastMessage’ 방을 나갔어요.")
                LocalPreferences.setExitHabitRoomHomeToastMessageState(true)
                finish()
            }
        }
    }

    private fun initHabitRecordsObserver() {
        habitViewModel.habitRecordList.observe(this) {
            habitRecyclerViewAdapter.updateHabitList(it)
        }
    }

    private fun refreshData() {
        if (roomId != -1) {
            habitViewModel.getHabitRoomInfo(roomId)
        }
    }

    private fun setSwipeRefreshLayout() {
        with(binding.swipeHabitRefresh) {
            setColorSchemeColors(context.getColor(R.color.spark_pinkred))
            setOnRefreshListener {
                refreshData()
                isRefreshing = false
            }
        }
    }

    private fun initRVAdapter() {
        habitRecyclerViewAdapter = HabitRecyclerViewAdapter()
        binding.rvHabitTeamList.adapter = habitRecyclerViewAdapter
    }

    private fun initHabitBackBtnClickListener() {
        binding.btnHabitBack.setOnClickListener {
            finish()
        }
    }

    private fun initHabitMoreBtnClickListener() {
        binding.btnHabitMore.setOnClickListener {
            HabitMoreBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }

    private fun initHabitTodayBtnClickListener() {
        binding.btnHabitTodayCertification.setOnClickListener {
            HabitTodayBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }

    private fun checkUserGuideDialog() {
        if (habitViewModel.getUserGuideDialogState()) {
            val bundle = Bundle()
            bundle.apply {
                putBoolean("startPoint", HabitMoreBottomSheet.START_FROM_INIT_STATE)
            }
            UserGuideFragmentDialog().apply {
                arguments = bundle
            }.show(supportFragmentManager, "UserGuideDialog")
            habitViewModel.setUserGuideDialogState(false)
        }
    }

//    1.1.0 에서 삭제 다른기능으로 대체됨
//    private fun initHabitLifeLessDialog() {
//        val lifeDeductionCount = habitViewModel.habitInfo.value?.lifeDeductionCount ?: 0
//        if (lifeDeductionCount != 0) {
//            HabitLifeLessDialogFragment(lifeDeductionCount).show(
//                supportFragmentManager,
//                "LifeLessDialog"
//            )
//        }
//    }

    private fun initGroupTeamLifeClickListener() {
        val lifeList = listOf<View>(
            binding.ivHabitTeamlifeFirst,
            binding.ivHabitTeamlifeSecond,
            binding.ivHabitTeamlifeThird
        )

        lifeList.forEach {
            it.setOnSingleClickListener {
                // 타임라인 바텀시트 넣을꺼임
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}
