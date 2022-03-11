package com.spark.android.ui.withdrawal

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentWithdrawalBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.popBackStack

class WithdrawalFragment : BaseFragment<FragmentWithdrawalBinding>(R.layout.fragment_withdrawal) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnWithdrawalBack.setOnClickListener { popBackStack() }
    }
}
