package com.spark.android.ui.waitingroom

import android.content.ClipboardManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentWaitingRoomBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.AnimationUtil
import android.content.ClipData

import android.content.Context.CLIPBOARD_SERVICE
import androidx.fragment.app.viewModels
import com.spark.android.ui.setpurpose.SetPurposeFragment
import com.spark.android.ui.waitingroom.adapter.WaitingRoomRecyclerViewAdapter
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
    private var startPoint by Delegates.notNull<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initExtra()
        binding.startPoint = startPoint
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

    private fun initExtra() {
        roomId = arguments?.getInt("roomId", -1) ?: -1
        startPoint = arguments?.getBoolean("startPoint") ?: false
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
            AnimationUtil.openToastAnimation(binding.tvWaitingRoomToast)
            Handler(Looper.getMainLooper()).postDelayed({
                AnimationUtil.closeToastAnimation(
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
            waitingRoomViewModel.startHabit(roomId)
            requireActivity().finish()
        }
    }

    private fun initSetPurposeButtonListener() {
        binding.btnWaitingRoomEditPurpose.setOnClickListener {
            val setPurposeFragment = SetPurposeFragment()

            var bundle = Bundle()
            bundle.putInt("roomId", roomId)
            bundle.putString("roomName", waitingRoomViewModel.waitingRoomInfo.value?.roomName)
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
            AnimationUtil.rotateAnimation(binding.btnWaitingRoomRefresh)
            binding.btnWaitingRoomRefresh.isEnabled = false
            Handler(Looper.getMainLooper()).postDelayed({
                binding.btnWaitingRoomRefresh.isEnabled = true
            }, AnimationUtil.ROTATE_TIME)
            waitingRoomViewModel.getRefreshInfo(roomId)
            waitingRoomViewModel.refreshInfo.observe(this) {
                waitingRoomRecyclerViewAdapter.members.clear()
                waitingRoomRecyclerViewAdapter.members.addAll(
                    it
                )
                waitingRoomRecyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }
}