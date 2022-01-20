package com.spark.android.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.spark.android.R
import com.spark.android.databinding.ActivityMainBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.joincode.inputcode.InputCodeFragmentDialog
import com.spark.android.ui.main.viewmodel.MainViewModel
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_FEED
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_HOME
import com.spark.android.ui.main.viewmodel.MainViewModel.Companion.TAB_STORAGE
import com.spark.android.ui.makeroom.MakeRoomActivity
import com.spark.android.util.FloatingAnimationUtil
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel by viewModels<MainViewModel>()
    private var fabState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.activity = this
        binding.mainViewModel = mainViewModel
        initStatusBarStyle()
        initBindingVariable()
        initFloatingButtonClickListener()
        initTabPositionObserver()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.initTabPositionHome()
    }

    private fun initStatusBarStyle() {
        initStatusBarColor(R.color.spark_white)
        initStatusBarTextColorToWhite()
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
                        R.id.feedFragment -> R.id.action_feedFragment_self
                        R.id.homeMainFragment -> R.id.action_homeMainFragment_to_feedFragment
                        R.id.storageFragment -> R.id.action_storageFragment_to_feedFragment
                        else -> throw IllegalStateException()
                    }
                    TAB_HOME -> when (findNavController().currentDestination?.id) {
                        R.id.feedFragment -> R.id.action_feedFragment_to_homeMainFragment
                        R.id.homeMainFragment -> R.id.action_homeMainFragment_self
                        R.id.storageFragment -> R.id.action_storageFragment_to_homeMainFragment
                        else -> throw IllegalStateException()
                    }
                    TAB_STORAGE -> when (findNavController().currentDestination?.id) {
                        R.id.feedFragment -> R.id.action_feedFragment_to_storageFragment
                        R.id.homeMainFragment -> R.id.action_homeMainFragment_to_storageFragment
                        R.id.storageFragment -> R.id.action_storageFragment_self
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

}