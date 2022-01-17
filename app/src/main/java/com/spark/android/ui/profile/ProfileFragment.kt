package com.spark.android.ui.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentProfileBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.main.MainActivity
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.STOP_SIGNUP_MODE
import com.spark.android.util.EventObserver
import com.spark.android.util.KeyBoardUtil
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import com.spark.android.util.popBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileViewModel = profileViewModel
        initStatusBarStyle()
        hideKeyBoard()
        initPictureBtnClickListener()
        initQuitBtnClickListener()
        initKakaoUserIdObserver()
        initSuccessSignUpObserver()
    }

    private fun initStatusBarStyle() {
        requireActivity().initStatusBarColor(R.color.spark_white)
        requireActivity().initStatusBarTextColorToWhite()
    }

    private fun hideKeyBoard() {
        binding.layoutProfile.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun initPictureBtnClickListener() {
        binding.btnProfileGetPhoto.setOnClickListener {
            ProfileBottomSheet(true).show(parentFragmentManager, this.javaClass.name)
        }
    }

    private fun initQuitBtnClickListener() {
        binding.btnProfileQuit.setOnClickListener {
            DialogUtil(STOP_SIGNUP_MODE) {
                popBackStack()
            }.show(parentFragmentManager, this.javaClass.name)
        }
    }

    private fun initKakaoUserIdObserver() {
        profileViewModel.kakaoUserId.observe(viewLifecycleOwner) {
            profileViewModel.postSignUp()
        }
    }

    private fun initSuccessSignUpObserver() {
        profileViewModel.successSignUp.observe(viewLifecycleOwner, EventObserver { successSignUp ->
            if (successSignUp) {
                requireActivity().finish()
                requireContext().startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        })
    }
}
