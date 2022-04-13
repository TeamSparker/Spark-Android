package com.spark.android.ui.habit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.data.remote.LocalPreferences
import com.spark.android.databinding.BottomSheetHabitMoreBinding
import com.spark.android.ui.habit.userguide.UserGuideFragmentDialog
import com.spark.android.ui.habit.viewmodel.HabitViewModel
import com.spark.android.util.DialogEditTextUtil

class HabitMoreBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetHabitMoreBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))
    private val habitViewModel by activityViewModels<HabitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = BottomSheetHabitMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        initGoalBtnClickListener()
        initExitBtnClickListener()
        initUserGuideBtnClickListener()
    }

    private fun initGoalBtnClickListener() {
        binding.tvHabitMoreGoal.setOnClickListener {
            val intent = Intent(context, HabitGoalManageActivity::class.java)
            intent.apply {
                putExtra("roomId", habitViewModel.habitInfo.value?.roomId)
                putExtra("roomName", habitViewModel.habitInfo.value?.roomName)
                putExtra("moment", habitViewModel.habitInfo.value?.moment)
                putExtra("purpose", habitViewModel.habitInfo.value?.purpose)
            }
            startActivity(intent)
            dismiss()
        }
    }

    private fun initExitBtnClickListener() {
        binding.tvHabitMoreExit.setOnClickListener {
            showExitDialog()
            dismiss()
        }
    }

    private fun initUserGuideBtnClickListener(){
        binding.tvHabitMoreUserGuide.setOnClickListener {
            UserGuideFragmentDialog().show(
                requireActivity().supportFragmentManager, "UserGuideDialog")
        }
    }

    private fun showExitDialog() {
        habitViewModel.habitInfo.value?.roomName?.let { roomName ->
            DialogEditTextUtil(DialogEditTextUtil.EXIT_HABIT_ROOM, roomName) {
                habitViewModel.leaveHabitRoom()
                setFragmentResult("exitHabitRoom", bundleOf("bundleKey" to "exit"))
            }.show(requireActivity().supportFragmentManager, this.javaClass.name)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}