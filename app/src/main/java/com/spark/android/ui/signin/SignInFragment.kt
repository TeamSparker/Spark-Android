package com.spark.android.ui.signin

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentSignInBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import com.spark.android.util.navigate

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().initStatusBarColor(R.color.spark_pinkred)
        requireActivity().initStatusBarTextColorToWhite()
        initKakaoLoginBtnClickListener()
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnSignInKakaoLogin.setOnClickListener {
            navigate(R.id.action_signInFragment_to_profileFragment)
        }
    }
}
