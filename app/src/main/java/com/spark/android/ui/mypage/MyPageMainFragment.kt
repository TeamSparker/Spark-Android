package com.spark.android.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.spark.android.BuildConfig
import com.spark.android.R
import com.spark.android.databinding.FragmentMyPageMainBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.navigate
import com.spark.android.util.navigateWithData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageMainFragment : BaseFragment<FragmentMyPageMainBinding>(R.layout.fragment_my_page_main) {
    private val myPageViewModel by viewModels<MyPageViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myPageViewModel = myPageViewModel
        myPageViewModel.getProfile()
        initBackBtnClickListener()
        initModifyProfileBtnClickListener()
        initAlarmSettingBtnClickListener()
        initPolicyBtnClickListener()
        initOpenSourceBtnClickListener()
        initWithdrawalBtnClickListener()
        initQuestionBtnClickListener()
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

    private fun initWithdrawalBtnClickListener() {
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
