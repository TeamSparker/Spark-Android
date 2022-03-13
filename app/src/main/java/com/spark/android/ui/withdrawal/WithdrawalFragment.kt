package com.spark.android.ui.withdrawal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentWithdrawalBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.popBackStack

class WithdrawalFragment : BaseFragment<FragmentWithdrawalBinding>(R.layout.fragment_withdrawal) {
    private val withdrawalViewModel by viewModels<WithdrawalViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.withdrawalViewModel = withdrawalViewModel
        initBackBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnWithdrawalBack.setOnClickListener { popBackStack() }
    }
}
