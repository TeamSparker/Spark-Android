package com.spark.android.ui.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetHabitSendSparkBinding
import com.spark.android.util.SendSparkToast

class HabitSendSparkBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHabitSendSparkBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetHabitSendSparkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initSendFirstBtnClickListener(view)
        initSendSecondBtnClickListener(view)
        initSendThirdBtnClickListener(view)
        initSendFourthBtnClickListener(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSendFirstBtnClickListener(view: View) {
        binding.btnHabitSendSparkMessageFirst.setOnClickListener {
            SendSparkToast.showToast(view.context)
            dismiss()
        }
    }

    private fun initSendSecondBtnClickListener(view: View) {
        binding.btnHabitSendSparkMessageSecond.setOnClickListener {
            SendSparkToast.showToast(view.context)
            dismiss()
        }
    }

    private fun initSendThirdBtnClickListener(view: View) {
        binding.btnHabitSendSparkMessageThird.setOnClickListener {
            SendSparkToast.showToast(view.context)
            dismiss()
        }
    }

    private fun initSendFourthBtnClickListener(view: View) {
        binding.btnHabitSendSparkMessageFourth.setOnClickListener {
            SendSparkToast.showToast(view.context)
            dismiss()
        }
    }
}