package com.spark.android.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.spark.android.R
import com.spark.android.databinding.ActivityMainBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.certify.CertifyActivity.Companion.FROM_CERTIFY_ACTIVITY
import com.spark.android.ui.feed.FeedFragmentDirections
import com.spark.android.ui.home.HomeMainFragmentDirections
import com.spark.android.ui.joincode.inputcode.InputCodeFragmentDialog
import com.spark.android.ui.main.viewmodel.MainViewModel
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_FEED
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_HOME
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_STORAGE
import com.spark.android.ui.makeroom.MakeRoomActivity
import com.spark.android.ui.storage.StorageFragmentDirections
import com.spark.android.ui.storage.StoragePhotoCollectionActivity.Companion.FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY
import com.spark.android.util.FloatingAnimationUtil
import com.spark.android.util.getToast
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()
    private var fabState = false
    private var backBtnWaitTime = 0L
    private val toast: Toast by lazy { getToast(getString(R.string.main_back_btn_msg)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.mainViewModel = mainViewModel
        initStatusBarStyle()
        initBindingVariable()
        initFloatingButtonClickListener()
        initTabPositionObserver()
        initBlackBgClickListener()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.initTabPositionHome()
        initTabPositionFromOthers()
    }

    override fun onBackPressed() {
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

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    private fun initTabPositionFromOthers() {
        when (intent.getStringExtra(FROM_WHERE)) {
            FROM_CERTIFY_ACTIVITY -> {
                mainViewModel.initTabPositionFeed()
            }
            FROM_STORAGE_PHOTO_COLLECTION_ACTIVITY -> {
                mainViewModel.initTabPositionStorage()
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
                        R.id.homeMainFragment -> HomeMainFragmentDirections.actionHomeMainFragmentToFeedFragment()
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
                        R.id.homeMainFragment -> HomeMainFragmentDirections.actionHomeMainFragmentToStorageFragment()
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
            FloatingAnimationUtil.toggleFab(
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
        FloatingAnimationUtil.closeFabAnimation(
            binding.fabHomeMain,
            binding.fabHomeMakeRoom,
            binding.fabHomeJoinCode,
            binding.layoutMainFabBackground,
            binding.tvFabMakeRoom,
            binding.tvFabJoinCode
        )
        fabState = !fabState
    }

    fun initMakeJoinCodeListener() {
        InputCodeFragmentDialog().show(
            supportFragmentManager, "InputCodeDialog"
        )
    }

    fun initBlackBgClickListener() {
        binding.layoutMainFabBackground.setOnClickListener {
            FloatingAnimationUtil.closeFabAnimation(
                binding.fabHomeMain,
                binding.fabHomeMakeRoom,
                binding.fabHomeJoinCode,
                binding.layoutMainFabBackground,
                binding.tvFabMakeRoom,
                binding.tvFabJoinCode
            )
            fabState = !fabState
        }
    }

    companion object {
        private const val BACK_BTN_WAIT_TIME = 2000L
        const val FROM_WHERE = "fromWhere"
    }
}
