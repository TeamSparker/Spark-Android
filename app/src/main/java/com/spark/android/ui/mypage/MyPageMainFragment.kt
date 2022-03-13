package com.spark.android.ui.mypage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentMyPageMainBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageMainFragment : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {
    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        myPageViewModel.getProfile()
        initBackBtnClickListener()
        initModifyProfileBtnClickListener()
        initAlarmSettingBtnClickListener()
        initWithdrawalBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnMyPageMainBack.setOnClickListener { requireActivity().finish() }
    }

    private fun initModifyProfileBtnClickListener() {
        binding.btnMyPageMainModifyProfile.setOnClickListener {
            navigate(R.id.action_myPageMainFragment_to_profileFragmentFromMyPage)
        }
    }

    private fun initAlarmSettingBtnClickListener() {
        binding.btnMyPageMainAlarm.setOnClickListener {
            navigate(R.id.action_myPageMainFragment_to_alarmSettingFragment)
        }
    }

    private fun initWithdrawalBtnClickListener() {
        binding.tvMyPageMainWithdrawal.setOnClickListener {
            navigate(R.id.action_myPageMainFragment_to_withdrawalFragment)
        }
    }
}