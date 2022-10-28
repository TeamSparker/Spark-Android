package com.teamsparker.android.ui.habit

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamsparker.android.R
import com.teamsparker.android.databinding.BottomSheetHabitTimeLineBinding
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitTimeLineBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetHabitTimeLineBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val habitViewModel by activityViewModels<HabitViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetHabitTimeLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTimeLineData()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    // 다이얼로그 높이 비율로 조정 코드
    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet!!.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getWindowHeight() * 449 / 736
        // 기기 높이 대비 비율 설정 부분!!
    }

    private fun getWindowHeight(): Int = resources.displayMetrics.heightPixels

    private fun initTimeLineData() {
        habitViewModel.getHabitRoomTimeLine()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
