package com.teamsparker.android.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.teamsparker.android.R
import com.teamsparker.android.SparkMessagingService.Companion.OPEN_FROM_PUSH_ALARM
import com.teamsparker.android.SparkMessagingService.Companion.ROOM_ID
import com.teamsparker.android.databinding.ActivityMainBinding
import com.teamsparker.android.ui.base.BaseActivity
import com.teamsparker.android.ui.certify.CertifyActivity.Companion.FROM_CERTIFY_ACTIVITY
import com.teamsparker.android.ui.feed.FeedFragmentDirections
import com.teamsparker.android.ui.feedreport.FeedReportActivity.Companion.FEED_REPORT_SUCCESS
import com.teamsparker.android.ui.feedreport.FeedReportActivity.Companion.FROM_FEED_REPORT_ACTIVITY
import com.teamsparker.android.ui.habit.HabitActivity
import com.teamsparker.android.ui.home.HomeMainFragmentDirections
import com.teamsparker.android.ui.joincode.inputcode.InputCodeFragmentDialog
import com.teamsparker.android.ui.main.viewmodel.MainViewModel
import com.teamsparker.android.ui.main.viewmodel.MainViewModel.Companion.TAB_FEED
import com.teamsparker.android.ui.main.viewmodel.MainViewModel.Companion.TAB_HOME
import com.teamsparker.android.ui.main.viewmodel.MainViewModel.Companion.TAB_STORAGE
import com.teamsparker.android.ui.makeroom.MakeRoomActivity
import com.teamsparker.android.ui.storage.StorageFragmentDirections
import com.teamsparker.android.util.AnimationUtil
import com.teamsparker.android.ui.storage.photo.StoragePhotoCollectionActivity.Companion.FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY
import com.teamsparker.android.util.NotificationCategory
import com.teamsparker.android.util.getToast
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()
    private var fabState = false
    private var backBtnWaitTime = 0L
    private val toast: Toast by lazy { getToast(getString(R.string.main_back_btn_msg)) }
    private var cardType: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.mainViewModel = mainViewModel
        initStatusBarStyle()
        initBindingVariable()
        initFloatingButtonClickListener()
        cardType = "progressingCard"
        if (IS_FROM_CARD) {
            cardType = intent.getStringExtra("cardType")
        }
        initTabPositionObserver()
        initBlackBgClickListener()
    }

    override fun onResume() {
        super.onResume()
        if (intent.getStringExtra(OPEN_FROM_PUSH_ALARM) != null) {
            when (intent.getStringExtra(OPEN_FROM_PUSH_ALARM)) {
                NotificationCategory.CERTIFICATION.category -> {
                    mainViewModel.initTabPositionFeed()
                    intent.removeExtra(OPEN_FROM_PUSH_ALARM)
                }
                NotificationCategory.SPARK.category,
                NotificationCategory.REMIND.category,
                NotificationCategory.ROOM_START.category,
                NotificationCategory.CONSIDER.category -> {
                    mainViewModel.initTabPositionHome()
                    moveAfterOpenPushAlarm()
                }
            }
        } else {
            mainViewModel.initTabPositionHome()
            initTabPositionFromOthers()
        }
    }

    override fun onBackPressed() {
        if (fabState) {
            AnimationUtil.closeFabAnimation(
                binding.fabHomeMain,
                binding.fabHomeMakeRoom,
                binding.fabHomeJoinCode,
                binding.layoutMainFabBackground,
                binding.tvFabMakeRoom,
                binding.tvFabJoinCode
            )
            fabState = !fabState
            initBindingVariable()
        } else {
            //한번더 뒤로가기 눌렀을때 앱꺼지게하는 코드(주석 이창환이 몰라서 달아놓음)
            if (System.currentTimeMillis() - backBtnWaitTime >= BACK_BTN_WAIT_TIME) {
                backBtnWaitTime = System.currentTimeMillis()
                toast.show()
            } else {
                toast.cancel()
                ActivityCompat.finishAffinity(this)
                System.runFinalization()
                exitProcess(0)
            }
        }
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    private fun initTabPositionFromOthers() {
        when (intent.getStringExtra(FROM_WHERE)) {
            FROM_CERTIFY_ACTIVITY, FROM_FEED_REPORT_ACTIVITY -> {
                mainViewModel.initTabPositionFeed()
            }
            FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY -> {
                mainViewModel.initTabPositionStorage()
                cardType = "progressingCard"
                IS_FROM_CARD = false
            }
        }
        intent.removeExtra(FROM_WHERE)
    }

    private fun findNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container_main) as NavHostFragment
        return navHostFragment.navController
    }

    private fun initTabPositionObserver() {
        mainViewModel.tabPosition.observe(this) { position ->
            findNavController().navigate(
                when (position) {
                    TAB_FEED -> when (findNavController().currentDestination?.id) {
                        R.id.feedFragment -> FeedFragmentDirections.actionFeedFragmentSelf()
                        R.id.homeMainFragment -> {
                            val feedReportSuccess =
                                intent.getBooleanExtra(FEED_REPORT_SUCCESS, false)
                            intent.removeExtra(FEED_REPORT_SUCCESS)
                            HomeMainFragmentDirections.actionHomeMainFragmentToFeedFragment(
                                feedReportSuccess
                            )
                        }
                        R.id.storageFragment -> StorageFragmentDirections.actionStorageFragmentToFeedFragment()
                        else -> throw IllegalStateException()
                    }
                    TAB_HOME -> when (findNavController().currentDestination?.id) {
                        R.id.feedFragment -> FeedFragmentDirections.actionFeedFragmentToHomeMainFragment()
                        R.id.homeMainFragment -> HomeMainFragmentDirections.actionHomeMainFragmentSelf()
                        R.id.storageFragment -> StorageFragmentDirections.actionStorageFragmentToHomeMainFragment()
                        else -> throw IllegalStateException()
                    }
                    TAB_STORAGE -> when (findNavController().currentDestination?.id) {
                        R.id.feedFragment -> FeedFragmentDirections.actionFeedFragmentToStorageFragment()
                        R.id.homeMainFragment -> HomeMainFragmentDirections.actionHomeMainFragmentToStorageFragment(
                            cardType
                        )
                        R.id.storageFragment -> StorageFragmentDirections.actionStorageFragmentSelf()
                        else -> throw IllegalStateException()
                    }
                    else -> throw IllegalStateException()
                }
            )
        }
    }

    private fun initFloatingButtonClickListener() {
        binding.fabHomeMain.setOnClickListener() {
            AnimationUtil.toggleFab(
                binding.fabHomeMain,
                binding.fabHomeMakeRoom,
                binding.fabHomeJoinCode,
                binding.layoutMainFabBackground,
                binding.tvFabMakeRoom,
                binding.tvFabJoinCode,
                fabState
            )
            fabState = !fabState
            initBindingVariable()
        }
    }

    private fun initBindingVariable() {
        binding.fabState = fabState
    }

    fun initMakeRoomClickListener() {
        val intent = Intent(this, MakeRoomActivity::class.java)
        startActivity(intent)
        AnimationUtil.closeFabAnimation(
            binding.fabHomeMain,
            binding.fabHomeMakeRoom,
            binding.fabHomeJoinCode,
            binding.layoutMainFabBackground,
            binding.tvFabMakeRoom,
            binding.tvFabJoinCode
        )
        fabState = !fabState
        initBindingVariable()
    }

    fun initMakeJoinCodeListener() {
        InputCodeFragmentDialog().show(
            supportFragmentManager, "InputCodeDialog"
        )
        AnimationUtil.closeFabAnimation(
            binding.fabHomeMain,
            binding.fabHomeMakeRoom,
            binding.fabHomeJoinCode,
            binding.layoutMainFabBackground,
            binding.tvFabMakeRoom,
            binding.tvFabJoinCode
        )
        fabState = !fabState
        initBindingVariable()
    }

    private fun initBlackBgClickListener() {
        binding.layoutMainFabBackground.setOnClickListener {
            AnimationUtil.closeFabAnimation(
                binding.fabHomeMain,
                binding.fabHomeMakeRoom,
                binding.fabHomeJoinCode,
                binding.layoutMainFabBackground,
                binding.tvFabMakeRoom,
                binding.tvFabJoinCode
            )
            fabState = !fabState
            initBindingVariable()
        }
    }

    private fun moveAfterOpenPushAlarm() {
        when (intent.getStringExtra(OPEN_FROM_PUSH_ALARM)) {
            NotificationCategory.SPARK.category,
            NotificationCategory.REMIND.category,
            NotificationCategory.ROOM_START.category,
            NotificationCategory.CONSIDER.category -> {
                startActivity(Intent(this, HabitActivity::class.java).apply {
                    putExtra(ROOM_ID, intent.getIntExtra(ROOM_ID, -1))
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                })
            }
        }
        intent.removeExtra(OPEN_FROM_PUSH_ALARM)
    }

    companion object {
        private const val BACK_BTN_WAIT_TIME = 2000L
        const val FROM_WHERE = "fromWhere"
        var IS_FROM_CARD = false
    }
}
