package com.spark.android.ui.habit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetHabitTodayBinding
import com.spark.android.ui.certify.CertifyActivity
import com.spark.android.ui.timer.TimerStartActivity

class HabitTodayBottomSheet(private val remainCount: Int) : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHabitTodayBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

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
        binding.remainCount = remainCount
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initCertifyBtnClickListener()
        initConsiderBtnClickListener()
    }

    private fun initCertifyBtnClickListener() {
        binding.btnHabitTodayCertificationNow.setOnClickListener {
            // if 문으로 스톱워치 or 사진인증 이동
            val intent = Intent(context, TimerStartActivity::class.java)
            startActivity(intent)
            dismiss()
        }
    }

    private fun initConsiderBtnClickListener() {
        binding.btnHabitTodayConsider.setOnClickListener {
            // 고민중
            dismiss()
        }
    }

    private fun initRestBtnClickListener() {
        binding.btnHabitTodayRest.setOnClickListener {
            // 쉴래요
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
