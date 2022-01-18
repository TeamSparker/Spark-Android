package com.spark.android.ui.waitingroom

import android.content.ClipboardManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spark.android.R
import com.spark.android.databinding.FragmentWaitingRoomBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.FloatingAnimationUtil
import android.content.ClipData
import android.content.Context

import android.content.Context.CLIPBOARD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.ui.setpurpose.SetPurposeFragment
import com.spark.android.ui.waitingroom.adapter.WaitingRoomRecyclerViewAdapter
import com.spark.android.ui.waitingroom.data.WaitingData
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class WaitingRoomFragment :
    BaseFragment<FragmentWaitingRoomBinding>(R.layout.fragment_waiting_room) {

    private lateinit var waitingRoomRecyclerViewAdapter: WaitingRoomRecyclerViewAdapter
    private var tooltipState = false
    private val waitingRoomViewModel by viewModels<WaitingRoomViewModel>()
    private var roomId by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getRoomId()

        if (roomId != null) {
            waitingRoomViewModel.getWaitingRoomInfo(roomId)
        }
        initWatitingRoomRecyclerViewAdapter()

        waitingRoomViewModel.waitingRoomInfo.observe(this) {
            binding.waitingRoomViewModel = waitingRoomViewModel
            updateWatitingRoomRecyclerViewAdapter()
            initClipBoard()
            initTooltipButton()
        }

        initMakeRoomButtonListener()
        initSetPurposeButtonListener()
        initMoveHomeButtonListener()
        initRefreshButtonListener()
    }

    private fun getRoomId() {
        roomId = arguments?.getInt("roomId", -1) ?: -1
    }

    private fun initClipBoard() {
        binding.btnWaitingRoomCopyCode.setOnClickListener {
            val clipboard: ClipboardManager =
                requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "code",
                waitingRoomViewModel.waitingRoomInfo.value?.roomCode
            )
            clipboard.setPrimaryClip(clip)

            binding.tvWaitingRoomToast.visibility = View.VISIBLE
            FloatingAnimationUtil.openToastAnimation(binding.tvWaitingRoomToast)
            Handler(Looper.getMainLooper()).postDelayed({
                FloatingAnimationUtil.closeToastAnimation(
                    binding.tvWaitingRoomToast
                )

            }, 2000)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.tvWaitingRoomToast.visibility = View.GONE
            }, 3000)
        }
    }

    private fun initWatitingRoomRecyclerViewAdapter() {


        waitingRoomRecyclerViewAdapter = WaitingRoomRecyclerViewAdapter()

        binding.rvWaitingRoomMembers.adapter = waitingRoomRecyclerViewAdapter


    }

    private fun updateWatitingRoomRecyclerViewAdapter() {
        waitingRoomRecyclerViewAdapter.members.clear()
        waitingRoomViewModel.waitingRoomInfo.value?.let {
            waitingRoomRecyclerViewAdapter.members.addAll(
                it.members
            )
        }
        waitingRoomRecyclerViewAdapter.notifyDataSetChanged()
    }

    private fun initTooltipButton() {
        binding.btnWaitingRoomInfo.setOnClickListener {
            if (waitingRoomViewModel.waitingRoomInfo.value?.fromStart == true) {
                binding.ivWaitingRoomTooltipTimer.visibility = View.VISIBLE
                binding.tvWaitingRoomTooltipTimerText.visibility = View.VISIBLE
                tooltipState = true
            } else {
                binding.ivWaitingRoomTooltipPicture.visibility = View.VISIBLE
                binding.tvWaitingRoomTooltipPictureText.visibility = View.VISIBLE
                tooltipState = true
            }
        }

        binding.layoutWaitingRoom.setOnClickListener {
            if (tooltipState) {
                if (waitingRoomViewModel.waitingRoomInfo.value?.fromStart == true) {
                    binding.ivWaitingRoomTooltipTimer.visibility = View.GONE
                    binding.tvWaitingRoomTooltipTimerText.visibility = View.GONE
                    tooltipState = false
                } else {
                    binding.ivWaitingRoomTooltipPicture.visibility = View.GONE
                    binding.tvWaitingRoomTooltipPictureText.visibility = View.GONE
                    tooltipState = false
                }
            }
        }
    }

    private fun initMakeRoomButtonListener() {
        binding.btnWaitingRoomStartHabit.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun initSetPurposeButtonListener() {
        binding.btnWaitingRoomEditPurpose.setOnClickListener {
            val setPurposeFragment = SetPurposeFragment()

            var bundle = Bundle()
            bundle.putInt("roomId", roomId)
            setPurposeFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, setPurposeFragment).commit()
        }
    }

    private fun initMoveHomeButtonListener() {
        binding.btnWaitingRoomMoveHome.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun initRefreshButtonListener() {
        binding.btnWaitingRoomRefresh.setOnClickListener {
            waitingRoomViewModel.getRefreshInfo(roomId)
            waitingRoomViewModel.refreshInfo.observe(this) {
                waitingRoomRecyclerViewAdapter.members.clear()
                waitingRoomRecyclerViewAdapter.members.addAll(
                    it
                )
            }
        }
    }
}