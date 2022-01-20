package com.spark.android.ui.habit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.BottomSheetHabitSendSparkBinding
import com.spark.android.ui.habit.viewmodel.HabitViewModel
import com.spark.android.util.SendSparkToast

class HabitSendSparkBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHabitSendSparkBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))
    var selectedItemId = -1
    var selectedItemNickname = ""

    private val habitViewModel by activityViewModels<HabitViewModel>()

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

        binding.habitViewModel = habitViewModel
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
            habitViewModel.postSendSpark(binding.btnHabitSendSparkMessageFirst.text.toString(),
                selectedItemId)
            SendSparkToast.showToast(view.context, selectedItemNickname)
            dismiss()
        }
    }

    private fun initSendSecondBtnClickListener(view: View) {
        binding.btnHabitSendSparkMessageSecond.setOnClickListener {
            habitViewModel.postSendSpark(binding.btnHabitSendSparkMessageSecond.text.toString(),
                selectedItemId)
            SendSparkToast.showToast(view.context, selectedItemNickname)
            dismiss()
        }
    }

    private fun initSendThirdBtnClickListener(view: View) {
        binding.btnHabitSendSparkMessageThird.setOnClickListener {
            habitViewModel.postSendSpark(binding.btnHabitSendSparkMessageThird.text.toString(),
                selectedItemId)
            SendSparkToast.showToast(view.context, selectedItemNickname)
            dismiss()
        }
    }

    private fun initSendFourthBtnClickListener(view: View) {
        binding.btnHabitSendSparkMessageFourth.setOnClickListener {
            habitViewModel.postSendSpark(binding.btnHabitSendSparkMessageFourth.text.toString(),
                selectedItemId)
            SendSparkToast.showToast(view.context, selectedItemNickname)
            dismiss()
        }
    }

    fun setSelectedItem(nickname: String, recordId: Int) {
        selectedItemId = recordId
        selectedItemNickname = nickname
    }

}