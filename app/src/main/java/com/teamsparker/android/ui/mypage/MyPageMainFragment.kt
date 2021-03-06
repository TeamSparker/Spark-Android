package com.teamsparker.android.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.teamsparker.android.BuildConfig
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentMyPageMainBinding
import com.teamsparker.android.ui.auth.AuthActivity
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.util.EventObserver
import com.teamsparker.android.util.navigate
import com.teamsparker.android.util.navigateWithData
import com.teamsparker.android.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageMainFragment : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {
    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        myPageViewModel.getProfile()
        initIsSuccessSignOutObserver()
        initBackBtnClickListener()
        initModifyProfileBtnClickListener()
        initAlarmSettingBtnClickListener()
        initPolicyBtnClickListener()
        initOpenSourceBtnClickListener()
        initWithdrawalTvClickListener()
        initQuestionBtnClickListener()
    }

    private fun initIsSuccessSignOutObserver() {
        myPageViewModel.isSuccessSignOut.observe(viewLifecycleOwner, EventObserver { isSuccess ->
            if (isSuccess) {
                requireContext().showToast(getString(R.string.my_page_sign_out_msg))
                ActivityCompat.finishAffinity(requireActivity())
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            }
        })
    }

    private fun initBackBtnClickListener() {
        binding.btnMyPageMainBack.setOnClickListener { requireActivity().finish() }
    }

    private fun initModifyProfileBtnClickListener() {
        binding.btnMyPageMainModifyProfile.setOnClickListener {
            myPageViewModel.profileData.value?.let { profile ->
                navigateWithData(
                    MyPageMainFragmentDirections.actionMyPageMainFragmentToProfileFragmentFromMyPage(
                        nickname = profile.nickname,
                        profileImgUrl = profile.profileImg
                    )
                )
            }
        }
    }

    private fun initAlarmSettingBtnClickListener() {
        binding.btnMyPageMainAlarm.setOnClickListener {
            navigate(R.id.action_myPageMainFragment_to_alarmSettingFragment)
        }
    }

    private fun initPolicyBtnClickListener() {
        binding.btnMyPageMainPolicy.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_my_page_policy)))
            )
        }
    }

    private fun initOpenSourceBtnClickListener() {
        binding.btnMyPageMainOpenSource.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_open_source)))
            )
        }
    }

    private fun initWithdrawalTvClickListener() {
        binding.tvMyPageMainWithdrawal.setOnClickListener {
            navigate(R.id.action_myPageMainFragment_to_withdrawalFragment)
        }
    }

    private fun initQuestionBtnClickListener() {
        binding.btnMyPageMainQuestion.setOnClickListener {
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "plain/text"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.question_email)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.question_subject))
                putExtra(
                    Intent.EXTRA_TEXT,
                    getString(
                        R.string.question_content,
                        getDeviceName(),
                        Build.VERSION.SDK_INT.toString(),
                        BuildConfig.VERSION_NAME
                    )
                )
            })
        }
    }

    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(str: String?): String {
        if (str == null || str.isEmpty()) {
            return ""
        }
        val first = str[0]
        return if (Character.isUpperCase(first)) {
            str
        } else {
            Character.toUpperCase(first).toString() + str.substring(1)
        }
    }
}
