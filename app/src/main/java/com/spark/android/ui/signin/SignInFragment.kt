package com.spark.android.ui.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import com.spark.android.R
import com.spark.android.databinding.FragmentSignInBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.navigate

class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()
        initKakaoLoginBtnClickListener()
    }

    private fun setStatusBarColor() {
        requireActivity().window.statusBarColor = getColor(requireContext(), R.color.spark_pinkred)
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnSignInKakaoLogin.setOnClickListener {
            navigate(R.id.action_signInFragment_to_profileFragment)
        }
    }
}
