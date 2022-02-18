package com.spark.android.ui.habit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetHabitTodayBinding
import com.spark.android.ui.certify.CertifyBottomSheet
import com.spark.android.ui.certify.CertifyMode
import com.spark.android.ui.habit.viewmodel.HabitViewModel
import com.spark.android.ui.timer.TimerStartActivity

class HabitTodayBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHabitTodayBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val habitViewModel by activityViewModels<HabitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetHabitTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        binding.habitViewModel = habitViewModel
        initCertifyBtnClickListener()
        initConsiderBtnClickListener()
        initRestBtnClickListener()
    }

    private fun showCertifyBottomSheet() {
        val certifyBottomSheet = CertifyBottomSheet()
        val bundle = Bundle()
        habitViewModel.habitInfo.value?.roomId?.let { it1 -> bundle.putInt("roomId", it1) }
        bundle.putString("roomName", habitViewModel.habitInfo.value?.roomName.toString())
        bundle.putString("profileImgUrl", habitViewModel.habitInfo.value?.myRecord?.profileImg.toString())
        bundle.putString("nickname", habitViewModel.habitInfo.value?.myRecord?.nickname.toString())
        bundle.putInt("certifyMode", CertifyMode.ONLY_CAMERA_MODE)
        bundle.putBoolean("onlyCameraInitial", true)
        certifyBottomSheet.arguments = bundle
        certifyBottomSheet.show(
            requireActivity().supportFragmentManager,
            this.javaClass.name
        )
    }

    private fun initCertifyBtnClickListener() {
        binding.btnHabitTodayCertificationNow.setOnClickListener {
            if (habitViewModel.habitInfo.value?.fromStart == true) {
                val intent = Intent(context, TimerStartActivity::class.java)
                intent.apply {
                    putExtra("nickname", habitViewModel.habitInfo.value?.myRecord?.nickname)
                    putExtra("profileImgUrl", habitViewModel.habitInfo.value?.myRecord?.profileImg)
                    putExtra("roomName", habitViewModel.habitInfo.value?.roomName.toString())
                    putExtra("roomId", habitViewModel.habitInfo.value?.roomId)
                    putExtra(
                        "profileImgUrl",
                        habitViewModel.habitInfo.value?.myRecord?.profileImg.toString()
                    )
                }
                startActivity(intent)
                dismiss()
            } else {
                dismiss()
                showCertifyBottomSheet()
            }
        }
    }

    private fun initConsiderBtnClickListener() {
        binding.btnHabitTodayConsider.setOnClickListener {
            habitViewModel.postStatus("CONSIDER")
            setFragmentResult("refreshHabitData", bundleOf("bundleKey" to "consider"))
            dismiss()
        }
    }

    private fun initRestBtnClickListener() {
        binding.btnHabitTodayRest.setOnClickListener {
            habitViewModel.postStatus("REST")
            setFragmentResult("refreshHabitData", bundleOf("bundleKey" to "rest"))
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
