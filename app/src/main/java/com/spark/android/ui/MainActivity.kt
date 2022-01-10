package com.spark.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityMainBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.home.FeedFragment
import com.spark.android.ui.home.HomeMainFragment
import com.spark.android.ui.home.StorageFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val feedFragment = FeedFragment()
    private val homeMainFragment = HomeMainFragment()
    private val storageFragment = StorageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTransactionEvent()
        initBottomNavigation()

    }

    private fun initTransactionEvent(){
        supportFragmentManager.beginTransaction().add(R.id.container_main,homeMainFragment)
    }

    private fun initBottomNavigation() {
        binding.bottomNavMain.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()

            when(it.itemId) {
                R.id.menu_feed -> {
                    transaction.replace(R.id.container_main,feedFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.menu_home -> {
                    transaction.replace(R.id.container_main,homeMainFragment)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    transaction.replace(R.id.container_main,storageFragment)
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

}