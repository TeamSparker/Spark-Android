package com.spark.android.ui.waitingroom.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spark.android.R
import com.spark.android.databinding.FragmentWaitingRoomBottomSheetBinding
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.WAITING_ROOM_BOTTOM_SHEET_GUEST
import com.spark.android.util.DialogUtil.Companion.WAITING_ROOM_BOTTOM_SHEET_HOST
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
        _binding = FragmentWaitingRoomBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDeleteRoomButtonClickListener()
    }

    private fun initDeleteRoomButtonClickListener(){
        binding.tvWaitingRoomBottomSheetDeleteRoom.setOnClickListener {
            if(waitingRoomBottomSheetViewModel.waitingRoomInfo.value?.reqUser?.isHost == true){
                DialogUtil(WAITING_ROOM_BOTTOM_SHEET_HOST){

                }.show(requireActivity().supportFragmentManager,this.javaClass.name)
            }else{
                DialogUtil(WAITING_ROOM_BOTTOM_SHEET_GUEST){

                }.show(requireActivity().supportFragmentManager,this.javaClass.name)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

