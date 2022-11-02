package com.teamsparker.android.ui.habit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.teamsparker.android.R
import com.teamsparker.android.databinding.BottomSheetHabitTimeLineBinding
import com.teamsparker.android.ui.habit.HabitActivity.Companion.REFRESH_DATA
import com.teamsparker.android.ui.habit.adapter.HabitTimeLineRecyclerViewAdapter
import com.teamsparker.android.ui.habit.viewmodel.HabitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitTimeLineBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetHabitTimeLineBinding? = null
    val binding get() = _binding ?: error(getString(R.string.binding_error))

    private val habitViewModel by activityViewModels<HabitViewModel>()
    private lateinit var habitTimeLineRecyclerViewAdapter: HabitTimeLineRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refreshDataOnHabitActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetHabitTimeLineBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTimeLineData()
        initHabitTimeLineRecyclerViewAdapter()
        updateRecyclerViewList()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupRatio(bottomSheetDialog)
        }
        return dialog
    }

    private fun refreshDataOnHabitActivity() {
        val refreshDataOnHabitActivity = arguments?.getSerializable(REFRESH_DATA) as () -> Unit
        refreshDataOnHabitActivity()
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

    private fun initHabitTimeLineRecyclerViewAdapter() {
        habitTimeLineRecyclerViewAdapter = HabitTimeLineRecyclerViewAdapter()
        binding.rvTimeLine.adapter = habitTimeLineRecyclerViewAdapter
    }

    private fun updateRecyclerViewList() {
        habitViewModel.timeLineList.observe(viewLifecycleOwner) {
            habitTimeLineRecyclerViewAdapter.submitList(it.timelines)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
