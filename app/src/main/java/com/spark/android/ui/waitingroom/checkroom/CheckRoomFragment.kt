package com.spark.android.ui.waitingroom.checkroom

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentCheckRoomBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.waitingroom.WaitingRoomFragment
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import com.spark.android.util.AnimationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class CheckRoomFragment : BaseFragment<FragmentCheckRoomBinding>(R.layout.fragment_check_room) {

    private val checkRoomViewModel by activityViewModels<WaitingRoomViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private var startPoint by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.checkRoomViewModel = checkRoomViewModel
        initExtra()
        if (roomId != null) {
            checkRoomViewModel.getWaitingRoomInfo(roomId)
        }
        checkRoomViewModel.waitingRoomInfo.observe(viewLifecycleOwner) {
            initClipBoard()
        }
        initMoveHomeButton()
        initMoveWaitingRoomButton()
    }

    private fun initExtra() {
        roomId = arguments?.getInt("roomId", -1) ?: -1
        startPoint = arguments?.getInt("startPoint",1) ?: 1
    }

    private fun initClipBoard() {
        binding.btnCheckRoomCopyCode.setOnClickListener {
            val clipboard: ClipboardManager =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "code",
                checkRoomViewModel.waitingRoomInfo.value?.roomCode
            )
            clipboard.setPrimaryClip(clip)
        }
    }

    private fun initMoveHomeButton(){
        binding.btnCheckRoomMoveHome.setOnClickListener{
            requireActivity().finish()
        }
    }

    private fun initMoveWaitingRoomButton(){
        binding.btnCheckRoomMoveWaitingRoom.setOnClickListener {
            val waitingRoomFragment = WaitingRoomFragment()
            var bundle = Bundle()
            bundle.putInt("roomId", roomId)
            bundle.putInt("startPoint", startPoint)
            waitingRoomFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, waitingRoomFragment).commit()
        }
    }
}