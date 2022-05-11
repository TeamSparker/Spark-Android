package com.teamsparker.android.ui.habit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamsparker.android.R
import com.teamsparker.android.databinding.BottomSheetHabitMoreBinding
import com.teamsparker.android.ui.habit.userguide.UserGuideFragmentDialog
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel
import com.teamsparker.android.util.DialogEditTextUtil

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

    private fun showExitDialog() {
        habitViewModel.habitInfo.value?.roomName?.let { roomName ->
            DialogEditTextUtil(DialogEditTextUtil.EXIT_HABIT_ROOM, roomName) {
                habitViewModel.leaveHabitRoom()
                habitViewModel.initExitSuccess(true)
            }.show(requireActivity().supportFragmentManager, this.javaClass.name)
        }
    }

    private fun initUserGuideBtnClickListener() {
        binding.tvHabitMoreUserGuide.setOnClickListener {
            var bundle = Bundle()
            bundle.apply {
                putBoolean("startPoint", START_FROM_HABIT_MORE_BUTTON)
            }
            UserGuideFragmentDialog().apply {
                arguments = bundle
            }.show(requireActivity().supportFragmentManager, "UserGuideDialog")
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val START_FROM_HABIT_MORE_BUTTON = true
        const val START_FROM_INIT_STATE = false
    }
}
