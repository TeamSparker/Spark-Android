package com.spark.android.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentSignInBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.EventObserver
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import com.spark.android.util.navigate
import com.spark.android.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInViewModel = signInViewModel
        initStatusBarStyle()
        initIsSuccessKakaoLoginObserver()
    }

    private fun initStatusBarStyle() {
        requireActivity().initStatusBarColor(R.color.spark_pinkred)
        requireActivity().initStatusBarTextColorToWhite()
    }

    private fun initIsSuccessKakaoLoginObserver() {
        signInViewModel.isSuccessKakaoLogin.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess) {
                navigate(R.id.action_signInFragment_to_profileFragment)
            } else {
                requireContext().showToast(getString(R.string.sign_in_kakao_login_failure_msg))
            }
        })
    }
}
