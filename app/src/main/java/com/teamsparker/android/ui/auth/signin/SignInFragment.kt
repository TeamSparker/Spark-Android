package com.teamsparker.android.ui.auth.signin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.data.remote.service.KakaoLoginService
import com.teamsparker.android.databinding.FragmentSignInBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.main.MainActivity
import com.teamsparker.android.util.EventObserver
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.SCREEN_SIGN_IN
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite
import com.teamsparker.android.util.navigateWithData
import com.teamsparker.android.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    @Inject
    lateinit var kakaoLoginService: KakaoLoginService
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseLogUtil.logScreenEvent(this.javaClass.name.split(".").last(), SCREEN_SIGN_IN)
        binding.signInViewModel = signInViewModel
        signInViewModel.resetDoorbellResponse()
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
        signInViewModel.isInitUserInfo.observe(viewLifecycleOwner, EventObserver { isInit ->
            if (isInit) {
                signInViewModel.getAccessToken()
            }
        })
    }

    private fun initDoorBellResponseObserver() {
        signInViewModel.doorbellResponse.observe(viewLifecycleOwner, EventObserver { response ->
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
        })
    }


}
