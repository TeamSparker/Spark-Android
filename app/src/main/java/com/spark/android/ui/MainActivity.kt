package com.spark.android.ui

import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityMainBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.home.FeedFragment
import com.spark.android.ui.home.HomeMainFragment
import com.spark.android.ui.storage.StorageFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var feedFragment: FeedFragment
    private lateinit var homeMainFragment: HomeMainFragment
    private lateinit var storageFragment: StorageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTransactionEvent()
        initBottomNavigation()

    }

    private fun initTransactionEvent() {
        feedFragment = FeedFragment()
        homeMainFragment = HomeMainFragment()
        storageFragment = StorageFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_main, homeMainFragment).commit()
    }

    private fun initBottomNavigation() {
        binding.bottomNavMain.selectedItemId = R.id.menu_home

        binding.bottomNavMain.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()

            when (it.itemId) {
                R.id.menu_feed -> {
                    transaction.replace(R.id.container_main, feedFragment).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_home -> {
                    transaction.replace(R.id.container_main, homeMainFragment).commit()
                    return@setOnItemSelectedListener true
                }
                else -> {
                    transaction.replace(R.id.container_main, storageFragment).commit()
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

}