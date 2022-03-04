package com.spark.android.ui.waitingroom

import android.animation.Animator
import android.animation.ObjectAnimator
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
import android.content.Intent
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnPause
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.spark.android.data.remote.entity.response.WaitingRoomInfoResponse
import com.spark.android.ui.main.MainActivity
import com.spark.android.ui.setpurpose.SetPurposeFragment
import com.spark.android.ui.storage.StoragePhotoCollectionActivity
import com.spark.android.ui.waitingroom.adapter.WaitingRoomRecyclerViewAdapter
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class WaitingRoomFragment :
    BaseFragment<FragmentWaitingRoomBinding>(R.layout.fragment_waiting_room) {

    private lateinit var waitingRoomRecyclerViewAdapter: WaitingRoomRecyclerViewAdapter
    private var tooltipState = false
    private val waitingRoomViewModel by activityViewModels<WaitingRoomViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private var startPoint by Delegates.notNull<Int>()
    private lateinit var toastAnimation: Animator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.waitingRoomViewModel = waitingRoomViewModel
        initExtra()
        binding.startPoint = startPoint
        if (startPoint != 2) {
            waitingRoomViewModel.getWaitingRoomInfo(roomId)
        }
        initWatitingRoomRecyclerViewAdapter()

        waitingRoomViewModel.waitingRoomInfo.observe(viewLifecycleOwner) {
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
        startPoint = arguments?.getInt("startPoint", 1) ?: 1
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

            binding.btnWaitingRoomCopyCode.isClickable = false
            binding.tvWaitingRoomToast.visibility = View.VISIBLE
            toastAnimation =
                requireNotNull(AnimationUtil.grayBoxToastAnimation(binding.tvWaitingRoomToast)).apply {
                    doOnEnd {
                        binding.tvWaitingRoomToast.visibility = View.GONE
                        binding.btnWaitingRoomCopyCode.isClickable = true
                    }
                    start()
                }
        }
    }

    private fun initWatitingRoomRecyclerViewAdapter() {
        waitingRoomRecyclerViewAdapter = WaitingRoomRecyclerViewAdapter()
        binding.rvWaitingRoomMembers.adapter = waitingRoomRecyclerViewAdapter
    }

    private fun updateWatitingRoomRecyclerViewAdapter() {
        waitingRoomViewModel.getRefreshInfo(roomId)
        waitingRoomViewModel.refreshInfo.observe(viewLifecycleOwner) {
            waitingRoomRecyclerViewAdapter.updateMemberList(it)
        }
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
            binding.btnWaitingRoomStartHabit.isClickable = false
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
            waitingRoomViewModel.getRefreshInfo(roomId)
            waitingRoomViewModel.refreshInfo.observe(viewLifecycleOwner) {
                waitingRoomRecyclerViewAdapter.updateMemberList(it)
                binding.btnWaitingRoomRefresh.isEnabled = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(::toastAnimation.isInitialized) {
            toastAnimation.cancel()
        }
    }
}
