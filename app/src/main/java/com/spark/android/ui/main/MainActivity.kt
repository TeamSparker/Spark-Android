package com.spark.android.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.spark.android.R
import com.spark.android.SparkApplication
import com.spark.android.databinding.ActivityMainBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.home.FeedFragment
import com.spark.android.ui.home.HomeMainFragment
import com.spark.android.ui.home.StorageFragment
import com.spark.android.ui.main.viewmodel.MainViewModel
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_FEED
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_HOME
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_STORAGE
import com.spark.android.util.FloatingAnimationUtil
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var feedFragment: FeedFragment
    private lateinit var homeMainFragment: HomeMainFragment
    private lateinit var storageFragment: StorageFragment
    private var fabState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mainViewModel = mainViewModel
        initStatusBarStyle()
        initTransactionEvent()
        initBindingVariable()
        initFloatingButtonClickListener()
        initTabPositionObserver()
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
    }

    private fun initTransactionEvent() {
        feedFragment = FeedFragment()
        homeMainFragment = HomeMainFragment()
        storageFragment = StorageFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_main, homeMainFragment)
            .commit()
    }

    private fun initTabPositionObserver() {
        mainViewModel.tabPosition.observe(this) { position ->
            when (position) {
                TAB_FEED -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main, feedFragment).commit()
                TAB_HOME -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main, homeMainFragment).commit()
                TAB_STORAGE -> supportFragmentManager.beginTransaction()
                    .replace(R.id.container_main, storageFragment).commit()
            }
        }
    }


    private fun initFloatingButtonClickListener() {
        binding.fabHomeMain.setOnClickListener() {
            FloatingAnimationUtil.toggleFab(
                binding.fabHomeMain,
                binding.fabHomeMakeRoom,
                binding.fabHomeJoinCode,
                binding.layoutMainFabBackground,
                fabState
            )
            fabState = !fabState
            initBindingVariable()
        }
    }

    private fun initBindingVariable() {
        binding.fabState = fabState
    }

}
