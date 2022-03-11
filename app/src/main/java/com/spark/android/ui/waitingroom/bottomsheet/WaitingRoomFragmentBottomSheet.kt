package com.spark.android.ui.waitingroom.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.FragmentWaitingRoomBottomSheetBinding
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.WAITING_ROOM_BOTTOM_SHEET_GUEST
import com.spark.android.util.DialogUtil.Companion.WAITING_ROOM_BOTTOM_SHEET_HOST
import com.spark.android.util.popBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WaitingRoomFragmentBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentWaitingRoomBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val waitingRoomBottomSheetViewModel by activityViewModels<WaitingRoomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_waiting_room_bottom_sheet,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.waitingRoomBottomSheetViewModel=waitingRoomBottomSheetViewModel
        initDeleteRoomButtonClickListener()
    }

    private fun initDeleteRoomButtonClickListener() {
        binding.tvWaitingRoomBottomSheetDeleteRoom.setOnClickListener {
            if (waitingRoomBottomSheetViewModel.waitingRoomInfo.value?.reqUser?.isHost == true) {
                DialogUtil(WAITING_ROOM_BOTTOM_SHEET_HOST) {
                    waitingRoomBottomSheetViewModel.deleteWaitingRoom(
                        waitingRoomBottomSheetViewModel.waitingRoomInfo.value!!.roomId
                    )
                    waitingRoomBottomSheetViewModel.setHomeToastMessage("'${waitingRoomBottomSheetViewModel.waitingRoomInfo.value!!.roomName}' 대기방이 삭제되었어요.")
                    waitingRoomBottomSheetViewModel.setHomeToastMessageState(true)
                    dismiss()
                    requireActivity().finish()
                }.show(requireActivity().supportFragmentManager, this.javaClass.name)
            } else {
                DialogUtil(WAITING_ROOM_BOTTOM_SHEET_GUEST) {
                    waitingRoomBottomSheetViewModel.leaveRoom(
                        waitingRoomBottomSheetViewModel.waitingRoomInfo.value!!.roomId
                    )
                    waitingRoomBottomSheetViewModel.setHomeToastMessage("'${waitingRoomBottomSheetViewModel.waitingRoomInfo.value!!.roomName}' 대기방을 나갔어요.")
                    waitingRoomBottomSheetViewModel.setHomeToastMessageState(true)
                    dismiss()
                    requireActivity().finish()
                }.show(requireActivity().supportFragmentManager, this.javaClass.name)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

