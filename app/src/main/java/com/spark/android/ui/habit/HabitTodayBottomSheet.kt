package com.spark.android.ui.habit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetHabitTodayBinding
import com.spark.android.ui.certify.CertifyBottomSheet
import com.spark.android.ui.habit.viewmodel.HabitViewModel
import com.spark.android.ui.timer.TimerStartActivity

class HabitTodayBottomSheet() : BottomSheetDialogFragment() {
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

    private fun initCertifyBtnClickListener() {
        binding.btnHabitTodayCertificationNow.setOnClickListener {
            if (habitViewModel.habitInfo.value?.fromStart == true) {
                val intent = Intent(context, TimerStartActivity::class.java)
                intent.putExtra("roomName", habitViewModel.habitInfo.value?.roomName.toString())
                intent.putExtra("fromStart",
                    habitViewModel.habitInfo.value?.fromStart.toString().toBoolean())
                startActivity(intent)
                dismiss()
            } else {
                dismiss()
                CertifyBottomSheet().show(requireActivity().supportFragmentManager, this.javaClass.name)
            }
        }
    }

    private fun initConsiderBtnClickListener() {
        binding.btnHabitTodayConsider.setOnClickListener {
            habitViewModel.postStatus("CONSIDER")
            dismiss()
        }
    }

    private fun initRestBtnClickListener() {
        binding.btnHabitTodayRest.setOnClickListener {
            habitViewModel.postStatus("REST")
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
