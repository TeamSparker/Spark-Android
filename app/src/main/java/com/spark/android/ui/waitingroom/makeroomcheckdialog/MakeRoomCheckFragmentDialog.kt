package com.spark.android.ui.waitingroom.makeroomcheckdialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentInputCodeDialogBinding
import com.spark.android.databinding.FragmentMakeRoomCheckDialogBinding
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_make_room_check_dialog, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMakeRoomButton()
        initDisMissButton()

    }

    private fun initMakeRoomButton(){
        binding.tvMakeRoomCheckDialogButtonMakeRoom.setOnClickListener {
            binding.tvMakeRoomCheckDialogButtonMakeRoom.isClickable = false
            makeRoomCheckFragmentDialogViewModel.waitingRoomInfo.value?.let { it ->
                makeRoomCheckFragmentDialogViewModel.startHabit(
                    it.roomId)
            }
            requireActivity().finish()
        }
    }

    private fun initDisMissButton(){
        binding.tvMakeRoomCheckDialogButtonDismiss.setOnClickListener {
            dismiss()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}