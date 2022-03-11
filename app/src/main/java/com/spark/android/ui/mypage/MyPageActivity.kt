package com.spark.android.ui.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.spark.android.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
    }
}
