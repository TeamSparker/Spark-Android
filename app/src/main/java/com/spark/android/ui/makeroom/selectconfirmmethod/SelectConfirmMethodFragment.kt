package com.spark.android.ui.makeroom.selectconfirmmethod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.spark.android.R
import com.spark.android.data.remote.entity.request.MakeRoomRequest
import com.spark.android.databinding.FragmentSelectConfirmMethodBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.makeroom.selectconfirmmethod.viewmodel.SelectConfirmMethodViewModel
import com.spark.android.ui.waitingroom.WaitingRoomActivity
import com.spark.android.ui.waitingroom.WaitingRoomActivity.Companion.START_FROM_CONFIRM_METHOD
import com.spark.android.ui.waitingroom.WaitingRoomFragment
import com.spark.android.util.DialogUtil
import com.spark.android.util.DialogUtil.Companion.CHECK_CONFIRM_MODE
import com.spark.android.util.popBackStack
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
                selectConfirmMethodViewModel.roomId.observe(viewLifecycleOwner) {
                    val intent = Intent(requireActivity(), WaitingRoomActivity::class.java).apply {
                        putExtra("roomId", selectConfirmMethodViewModel.roomId.value)
                        putExtra("startPoint", START_FROM_CONFIRM_METHOD)
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }
            }.show(requireActivity().supportFragmentManager,this.javaClass.name)
        }
    }

    private fun initBackButton() {
        binding.btnMakeRoomSelectConfirmMethodBack.setOnClickListener {
            popBackStack()
        }
    }
}