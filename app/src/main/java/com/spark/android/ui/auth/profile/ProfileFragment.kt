package com.spark.android.ui.auth.profile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentProfileBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.auth.profile.ProfileBottomSheet.Companion.DELETE_MODE
import com.spark.android.ui.auth.profile.ProfileBottomSheet.Companion.REQUEST_PROFILE_DIALOG
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.STOP_SIGNUP_MODE
import com.spark.android.util.EventObserver
import com.spark.android.util.KeyBoardUtil
import com.spark.android.util.MultiPartResolver
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite
import com.spark.android.util.popBackStack
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    @Inject
    lateinit var multiPartResolver: MultiPartResolver
    private val profileViewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileViewModel = profileViewModel
        profileViewModel.initKakaoUserId()
        profileViewModel.initFcmToken()
        initStatusBarStyle()
        hideKeyBoard()
        initIsFocused()
        initPictureBtnClickListener()
        initQuitBtnClickListener()
        initSuccessSignUpObserver()
        initFragmentResultListener()
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

    private fun initIsFocused() {
        binding.etProfileNickname.setOnFocusChangeListener { _, hasFocus ->
            profileViewModel.initNicknameFocused(hasFocus)
        }
    }

    private fun initPictureBtnClickListener() {
        binding.btnProfileGetPhoto.setOnClickListener {
            setFragmentResult(
                REQUEST_PROFILE_DIALOG,
                bundleOf(DELETE_MODE to requireNotNull(profileViewModel.deleteMode.value).peekContent())
            )
            ProfileBottomSheet().show(parentFragmentManager, this.javaClass.name)
        }
    }

    private fun initQuitBtnClickListener() {
        binding.btnProfileQuit.setOnClickListener {
            DialogUtil(STOP_SIGNUP_MODE) {
                popBackStack()
            }.show(parentFragmentManager, this.javaClass.name)
        }
    }

    private fun initSuccessSignUpObserver() {
        profileViewModel.successSignUp.observe(viewLifecycleOwner, EventObserver { successSignUp ->
            if (successSignUp) {
                requireContext().startActivity(
                    Intent(requireContext(), MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    })
                requireActivity().finish()
                profileViewModel.initIsLoading(false)
            }
        })
    }

    private fun initFragmentResultListener() {
        setFragmentResultListener(REQUEST_PROFILE_IMG_URI) { _, bundle ->
            val uri = bundle.get(PROFILE_IMG) as Uri
            profileViewModel.initProfileImgMultiPart(multiPartResolver.createImgMultiPart(uri))
            profileViewModel.initProfileImgUri(uri)
        }
        setFragmentResultListener(REQUEST_PROFILE_IMG_BITMAP) { _, bundle ->
            val bitmap = bundle.get(PROFILE_IMG) as Bitmap
            profileViewModel.initProfileImgMultiPart(multiPartResolver.createImgMultiPart(bitmap))
            profileViewModel.initProfileImgBitmap(bitmap)
        }
        setFragmentResultListener(REQUEST_PROFILE_DELETE) { _, _ ->
            profileViewModel.initProfileImgMultiPart(null)
            profileViewModel.deleteProfileImg()
        }
    }

    companion object {
        const val REQUEST_PROFILE_IMG_URI = "requestProfileImgUri"
        const val REQUEST_PROFILE_IMG_BITMAP = "requestProfileImgBitmap"
        const val REQUEST_PROFILE_DELETE = "requestProfileDelete"
        const val PROFILE_IMG = "profileImg"
    }
}
