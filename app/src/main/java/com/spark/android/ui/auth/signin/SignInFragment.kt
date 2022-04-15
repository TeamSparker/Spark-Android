package com.spark.android.ui.auth.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.data.remote.service.KakaoLoginService
import com.spark.android.databinding.FragmentSignInBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.main.MainActivity
import com.spark.android.util.EventObserver
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import com.spark.android.util.navigate
import com.spark.android.util.navigateWithData
import com.spark.android.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInViewModel = signInViewModel
        signInViewModel.addSourcesToIsInitUserInfo()
        initKakaoLoginBtnClickListener()
        initPolicyTvClickListener()
        initPersonalInfoTvClickListener()
        initStatusBarStyle()
        initIsSuccessKakaoLoginObserver()
        initIsInitUserInfoObserver()
        initDoorBellResponseObserver()
    }

    private fun initStatusBarStyle() {
        requireActivity().initStatusBarColor(R.color.spark_pinkred)
        requireActivity().initStatusBarTextColorToWhite()
    }

    private fun initKakaoLoginBtnClickListener() {
        binding.btnSignInKakaoLogin.setOnClickListener {
            startKakaoLogin()
        }
    }

    private fun initPolicyTvClickListener() {
        binding.tvSignInPolicy.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_splash_policy)))
            )
        }
    }

    private fun initPersonalInfoTvClickListener() {
        binding.tvSignInPersonalInfo.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_splash_personal_info)))
            )
        }
    }

    private fun startKakaoLogin() {
        kakaoLoginService.startKakaoLogin(signInViewModel.kakaoLoginCallback)
    }

    private fun initIsSuccessKakaoLoginObserver() {
        signInViewModel.isSuccessKakaoLogin.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess) {
                signInViewModel.initKakaoUserId()
                signInViewModel.initFcmToken()
            } else {
                requireContext().showToast(getString(R.string.sign_in_kakao_login_failure_msg))
            }
        })
    }

    private fun initIsInitUserInfoObserver() {
        signInViewModel.isInitUserInfo.observe(viewLifecycleOwner) { isInit ->
            if (isInit) {
                signInViewModel.getAccessToken()
            }
        }
    }

    private fun initDoorBellResponseObserver() {
        signInViewModel.doorbellResponse.observe(viewLifecycleOwner) { response ->
            if (response.isNew) {
                navigateWithData(SignInFragmentDirections.actionSignInFragmentToProfileFragment())
            } else {
                requireContext().showToast(getString(R.string.sign_in_complete_login_msg))
                signInViewModel.saveAccessToken()
                startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                })
                requireActivity().finish()
            }
        }
    }


}
