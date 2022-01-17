package com.spark.android.ui.certify

import android.os.Bundle
import com.spark.android.R
import com.spark.android.databinding.ActivityCertifyBinding
import com.spark.android.ui.base.BaseActivity
import com.spark.android.ui.habit.HabitMoreBottomSheet

class CertifyActivity : BaseActivity<ActivityCertifyBinding>(R.layout.activity_certify) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initCertifyPhotoBtnClickListener()
    }

    private fun initCertifyPhotoBtnClickListener() {
        binding.btnCertifyPhoto.setOnClickListener {
            CertifyBottomSheet().show(supportFragmentManager, this.javaClass.name)
        }
    }
}