package com.teamsparker.android.ui.makeroom.selectconfirmmethod

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.teamsparker.android.R
import com.teamsparker.android.data.remote.entity.request.MakeRoomRequest
import com.teamsparker.android.databinding.FragmentSelectConfirmMethodBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.makeroom.selectconfirmmethod.viewmodel.SelectConfirmMethodViewModel
import com.teamsparker.android.ui.waitingroom.WaitingRoomActivity
import com.teamsparker.android.ui.waitingroom.WaitingRoomActivity.Companion.START_FROM_CONFIRM_METHOD
import com.teamsparker.android.util.DialogUtil
import com.teamsparker.android.util.DialogUtil.Companion.CHECK_CONFIRM_MODE
import com.teamsparker.android.util.FirebaseLogUtil
import com.teamsparker.android.util.FirebaseLogUtil.CLICK_NEXT_CREATE_ROOM
import com.teamsparker.android.util.popBackStack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectConfirmMethodFragment :
    BaseFragment<FragmentSelectConfirmMethodBinding>(R.layout.fragment_select_confirm_method) {

    private val selectConfirmMethodViewModel by viewModels<SelectConfirmMethodViewModel>()
    private val args by navArgs<SelectConfirmMethodFragmentArgs>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectConfirmMethodViewModel = selectConfirmMethodViewModel
        initOpenWaitingRoom()
        initBackButton()
    }

    private fun initOpenWaitingRoom() {
        binding.btnMakeRoomSelectConfirmMethodEnterWaiting.setOnClickListener {
            DialogUtil(CHECK_CONFIRM_MODE) {
                binding.btnMakeRoomSelectConfirmMethodEnterWaiting.isClickable = false
                val requestData =
                    selectConfirmMethodViewModel.methodState.value?.let { methodState ->
                        args.roomName?.let { roomName ->
                            MakeRoomRequest(
                                roomName,
                                methodState
                            )
                        }
                    }

                if (requestData != null) {
                    selectConfirmMethodViewModel.makeRoom(requestData)
                }
                //GA ??????
                FirebaseLogUtil.logClickEventWithStartDate(CLICK_NEXT_CREATE_ROOM)
                selectConfirmMethodViewModel.roomId.observe(viewLifecycleOwner) {
                    val intent = Intent(requireActivity(), WaitingRoomActivity::class.java).apply {
                        putExtra("roomId", selectConfirmMethodViewModel.roomId.value)
                        putExtra("startPoint", START_FROM_CONFIRM_METHOD)
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }
            }.show(requireActivity().supportFragmentManager, this.javaClass.name)
        }
    }

    private fun initBackButton() {
        binding.btnMakeRoomSelectConfirmMethodBack.setOnClickListener {
            popBackStack()
        }
    }
}
