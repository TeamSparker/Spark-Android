package com.spark.android.ui.mypage

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentMyPageMainBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.navigate

class MyPageMainFragment : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBackBtnClickListener()
        initModifyProfileBtnClickListener()
    }

    private fun initBackBtnClickListener() {
        binding.btnMyPageMainBack.setOnClickListener { requireActivity().finish() }
    }

    private fun initModifyProfileBtnClickListener() {
        binding.btnMyPageMainModifyProfile.setOnClickListener {
            navigate(R.id.action_myPageMainFragment_to_profileFragmentFromMyPage)
        }
    }

}
