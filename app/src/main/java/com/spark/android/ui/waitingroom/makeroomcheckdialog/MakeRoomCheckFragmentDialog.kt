package com.spark.android.ui.waitingroom.makeroomcheckdialog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentMakeRoomCheckDialogBinding
import com.spark.android.ui.habit.HabitActivity
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import com.spark.android.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MakeRoomCheckFragmentDialog : DialogFragment() {

    private var _binding: FragmentMakeRoomCheckDialogBinding? = null
    private val binding get() = _binding!!
    private val makeRoomCheckFragmentDialogViewModel by activityViewModels<WaitingRoomViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_make_room_check_dialog,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMakeRoomButton()
        initDisMissButton()

    }

    override fun onStart() {
        super.onStart()
        setLayout()
    }

    private fun setLayout() {
        requireNotNull(dialog).apply {
            requireNotNull(window).apply {
                setLayout(
                    (resources.displayMetrics.widthPixels * 0.91).toInt(),
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                setBackgroundDrawableResource(R.drawable.shape_spark_white_fill_2_rect)
            }
        }
    }


    private fun initMakeRoomButton() {
        binding.tvMakeRoomCheckDialogButtonMakeRoom.setOnClickListener {
            binding.tvMakeRoomCheckDialogButtonMakeRoom.isClickable = false
            makeRoomCheckFragmentDialogViewModel.waitingRoomInfo.value?.let { it ->
                makeRoomCheckFragmentDialogViewModel.startHabit(
                    it.roomId
                )
            }
            val intent = Intent(requireActivity(), HabitActivity::class.java).apply {
                makeRoomCheckFragmentDialogViewModel.waitingRoomInfo.value?.let { waitingRoomInfoValue ->
                    putExtra(
                        "roomId",
                        waitingRoomInfoValue.roomId
                    )
                }
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }
            makeRoomCheckFragmentDialogViewModel.startHabitRoomState.observe(
                viewLifecycleOwner,
                EventObserver {
                    startActivity(intent)
                    requireActivity().finish()
                })
        }
    }


    private fun initDisMissButton() {
        binding.tvMakeRoomCheckDialogButtonDismiss.setOnClickListener {
            dismiss()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
