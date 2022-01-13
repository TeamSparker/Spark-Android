package com.spark.android.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.spark.android.R
import com.spark.android.SparkApplication
import com.spark.android.databinding.ActivityMainBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.home.FeedFragment
import com.spark.android.ui.home.HomeMainFragment
import com.spark.android.ui.home.StorageFragment
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var feedFragment: FeedFragment
    private lateinit var homeMainFragment: HomeMainFragment
    private lateinit var storageFragment: StorageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initStatusBarStyle()
        initTransactionEvent()
        initBottomNavigationFeedButton()
        initBottomNavigationHomeButton()
        initBottomNavigationStorageButton()

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

    private fun initBottomNavigationFeedButton() {
        binding.btnMainBottomNavFeed.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.container_main, feedFragment).commit()
            binding.btnMainBottomNavFeed.setImageResource(R.drawable.ic_bottom_navigation_feed_selected)
            binding.tvMainBottomNavFeed.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_pinkred))
            binding.btnMainBottomNavHome.setImageResource(R.drawable.ic_bottom_navigation_home)
            binding.tvMainBottomNavHome.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_gray))
            binding.btnMainBottomNavStorage.setImageResource(R.drawable.ic_bottom_navigation_storage)
            binding.tvMainBottomNavStorage.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_gray))
        }
    }

    private fun initBottomNavigationHomeButton() {
        binding.btnMainBottomNavHome.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.container_main, homeMainFragment).commit()
            binding.btnMainBottomNavFeed.setImageResource(R.drawable.ic_bottom_navigation_feed)
            binding.tvMainBottomNavFeed.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_gray))
            binding.btnMainBottomNavHome.setImageResource(R.drawable.ic_bottom_navigation_home_selected)
            binding.tvMainBottomNavHome.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_pinkred))
            binding.btnMainBottomNavStorage.setImageResource(R.drawable.ic_bottom_navigation_storage)
            binding.tvMainBottomNavStorage.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_gray))
        }
    }

    private fun initBottomNavigationStorageButton() {
        binding.btnMainBottomNavStorage.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.container_main, storageFragment).commit()
            binding.btnMainBottomNavFeed.setImageResource(R.drawable.ic_bottom_navigation_feed)
            binding.tvMainBottomNavFeed.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_gray))
            binding.btnMainBottomNavHome.setImageResource(R.drawable.ic_bottom_navigation_home)
            binding.tvMainBottomNavHome.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_gray))
            binding.btnMainBottomNavStorage.setImageResource(R.drawable.ic_bottom_navigation_storage_selected)
            binding.tvMainBottomNavStorage.setTextColor(ContextCompat.getColor(SparkApplication.ApplicationContext(), R.color.spark_dark_pinkred))
        }
    }

}
