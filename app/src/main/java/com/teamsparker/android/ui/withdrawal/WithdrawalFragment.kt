package com.teamsparker.android.ui.withdrawal

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentWithdrawalBinding
import com.teamsparker.android.ui.auth.AuthActivity
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.util.DialogUtil
import com.teamsparker.android.util.DialogUtil.Companion.WITHDRAWAL
import com.teamsparker.android.util.EventObserver
import com.teamsparker.android.util.popBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawalFragment : BaseFragment<FragmentWithdrawalBinding>(R.layout.fragment_withdrawal) {
    private val withdrawalViewModel by viewModels<WithdrawalViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.withdrawalViewModel = withdrawalViewModel
        initBackBtnClickListener()
        initWithdrawalBtnClickListener()
        initIsSuccessWithdrawalObserver()
    }

    private fun initBackBtnClickListener() {
        binding.btnWithdrawalBack.setOnClickListener { popBackStack() }
    }

    private fun initWithdrawalBtnClickListener() {
        binding.btnWithdrawalWithdraw.setOnClickListener {
            DialogUtil(WITHDRAWAL) {
                withdrawalViewModel.deleteUser()
            }.show(parentFragmentManager, this.javaClass.name)
        }
    }

    private fun initIsSuccessWithdrawalObserver() {
        withdrawalViewModel.isSuccessWithdraw.observe(viewLifecycleOwner, EventObserver { success ->
            if (success) {
                withdrawalViewModel.initIsLoading(false)
                ActivityCompat.finishAffinity(requireActivity())
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            } else {
                withdrawalViewModel.unLinkKakaoAccount()
            }
        })
    }
}
