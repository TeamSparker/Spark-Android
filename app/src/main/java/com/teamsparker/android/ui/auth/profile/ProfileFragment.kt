package com.teamsparker.android.ui.auth.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentProfileBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.main.MainActivity
import com.teamsparker.android.ui.auth.profile.ProfileBottomSheet.Companion.DELETE_MODE
import com.teamsparker.android.ui.auth.profile.ProfileBottomSheet.Companion.REQUEST_PROFILE_DIALOG
import com.teamsparker.android.util.DialogUtil
import com.teamsparker.android.util.DialogUtil.Companion.STOP_MODIFY_PROFILE
import com.teamsparker.android.util.DialogUtil.Companion.STOP_SIGNUP_MODE
import com.teamsparker.android.util.EventObserver
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.CLICK_FINISH_SIGN_UP
import com.teamsparker.android.util.KeyBoardUtil
import com.teamsparker.android.util.MultiPartResolver
import com.teamsparker.android.util.initStatusBarColor
import com.teamsparker.android.util.initStatusBarTextColorToWhite
import com.teamsparker.android.util.popBackStack
import com.teamsparker.android.util.useBitmapImg
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    @Inject
    lateinit var multiPartResolver: MultiPartResolver
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val args by navArgs<ProfileFragmentArgs>()
    private var fromCamera = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileViewModel = profileViewModel
        profileViewModel.initKakaoUserId()
        profileViewModel.initFcmToken()
        initModifyMode()
        initStatusBarStyle()
        initLayoutClickListener()
        initIsFocused()
        initQuitBtnClickListener()
        initOnBackPressed()
        initPictureBtnClickListener()
        initSuccessSignUpObserver()
        initOldProfileImgUrlObserver()
        initSuccessModifyObserver()
        initFragmentResultListener()
    }

    private fun initModifyMode() {
        binding.modifyMode = args.modifyMode
        if (args.modifyMode) {
            profileViewModel.initOldNickname(args.nickname)
            profileViewModel.initOldProfileImgUrl(args.profileImgUrl)
        } else {
            profileViewModel.deleteProfileImg()
        }
    }

    private fun initStatusBarStyle() {
        requireActivity().initStatusBarColor(R.color.spark_white)
        requireActivity().initStatusBarTextColorToWhite()
    }

    private fun initLayoutClickListener() {
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

    private fun showDialog() {
        DialogUtil(
            if (args.modifyMode) STOP_MODIFY_PROFILE else STOP_SIGNUP_MODE
        ) {
            popBackStack()
        }.show(parentFragmentManager, this.javaClass.name)
    }

    private fun initQuitBtnClickListener() {
        binding.btnProfileQuit.setOnClickListener {
            showDialog()
        }
    }

    private fun initOnBackPressed() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showDialog()
                }
            })
    }

    private fun initSuccessSignUpObserver() {
        profileViewModel.successSignUp.observe(viewLifecycleOwner, EventObserver { successSignUp ->
            if (successSignUp) {
                FirebaseLogUtil.logClickEvent(CLICK_FINISH_SIGN_UP)
                deleteImgFromCamera()
                requireContext().startActivity(
                    Intent(requireContext(), MainActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    })
                requireActivity().finish()
                profileViewModel.initIsLoading(false)
            }
        })
    }

    private fun initOldProfileImgUrlObserver() {
        profileViewModel.oldProfileImgUrl.observe(viewLifecycleOwner) { oldProfileImgUrl ->
            useBitmapImg(
                requireContext(),
                oldProfileImgUrl
            ) { bitmap ->
                profileViewModel.initProfileImgMultiPart(multiPartResolver.createImgMultiPart(bitmap))
            }
        }
    }

    private fun initSuccessModifyObserver() {
        profileViewModel.successModify.observe(viewLifecycleOwner, EventObserver { successModify ->
            if (successModify) {
                deleteImgFromCamera()
                popBackStack()
                profileViewModel.initIsLoading(false)
            }
        })
    }

    private fun initFragmentResultListener() {
        setFragmentResultListener(REQUEST_PROFILE_IMG_FROM_ALBUM) { _, bundle ->
            val uri = bundle.get(PROFILE_IMG) as Uri
            profileViewModel.initProfileImgMultiPart(multiPartResolver.createImgMultiPart(uri))
            profileViewModel.initProfileImgUri(uri)
        }
        setFragmentResultListener(REQUEST_PROFILE_IMG_FROM_CAMERA) { _, bundle ->
            val uri = bundle.get(PROFILE_IMG) as Uri
            profileViewModel.initProfileImgMultiPart(multiPartResolver.createImgMultiPart(uri))
            profileViewModel.initProfileImgUri(uri)
            fromCamera = true
        }
        setFragmentResultListener(REQUEST_PROFILE_DELETE) { _, _ ->
            profileViewModel.initProfileImgMultiPart(null)
            profileViewModel.deleteProfileImg()
        }
    }

    private fun deleteImgFromCamera(){
        if (fromCamera){
            profileViewModel.profileImgUri.value?.let {
                requireContext().contentResolver.delete(it, null, null)
            }
        }
    }

    companion object {
        const val REQUEST_PROFILE_IMG_FROM_ALBUM = "requestProfileImgFromAlbum"
        const val REQUEST_PROFILE_IMG_FROM_CAMERA = "requestProfileImgCamera"
        const val REQUEST_PROFILE_DELETE = "requestProfileDelete"
        const val PROFILE_IMG = "profileImg"
    }
}
