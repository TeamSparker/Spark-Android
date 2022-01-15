package com.spark.android.ui.signin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.kakao.sdk.user.UserApiClient
import com.spark.android.R
import com.spark.android.data.repository.SignInRepositoryImpl.Companion.KAKAO_ACCOUNT_LOGIN
import com.spark.android.data.repository.SignInRepositoryImpl.Companion.KAKAO_TALK_LOGIN
import com.spark.android.databinding.FragmentSignInBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import com.spark.android.util.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {
    private val signInViewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signInViewModel = signInViewModel
        initStatusBarStyle()
        initKakaoLoginStateObserver()
        initKakaoUserIdObserver()
    }

    private fun initStatusBarStyle() {
        requireActivity().initStatusBarColor(R.color.spark_pinkred)
        requireActivity().initStatusBarTextColorToWhite()
    }

    private fun initKakaoLoginStateObserver() {
        signInViewModel.kakaoLoginState.observe(viewLifecycleOwner) { kakaoLoginState ->
            when (kakaoLoginState) {
                KAKAO_TALK_LOGIN -> UserApiClient.instance.loginWithKakaoTalk(
                    requireContext(),
                    callback = signInViewModel.kakaoLoginCallback
                )
                KAKAO_ACCOUNT_LOGIN -> UserApiClient.instance.loginWithKakaoAccount(
                    requireContext(),
                    callback = signInViewModel.kakaoLoginCallback
                )
            }
            initKakaoUserId()
        }
    }

    private fun initKakaoUserIdObserver() {
        signInViewModel.kakaoUserId.observe(viewLifecycleOwner) {
            navigate(R.id.action_signInFragment_to_profileFragment)
        }
    }

    private fun initKakaoUserId() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e("kakao", "토큰 정보 보기 실패", error)
            } else if (tokenInfo != null) {
                signInViewModel.initKakaoUserId(requireNotNull(tokenInfo.id))
                Log.d(
                    "kakao", "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초"
                )
            }
        }
    }
}
