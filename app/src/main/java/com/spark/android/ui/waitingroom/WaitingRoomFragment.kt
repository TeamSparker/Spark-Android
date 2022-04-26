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
import com.spark.android.ui.setpurpose.SetPurposeFragment
import com.spark.android.ui.waitingroom.WaitingRoomActivity.Companion.START_FROM_CONFIRM_METHOD
import com.spark.android.ui.waitingroom.WaitingRoomActivity.Companion.START_FROM_HOME
import com.spark.android.ui.waitingroom.adapter.WaitingRoomRecyclerViewAdapter
import com.spark.android.ui.waitingroom.bottomsheet.WaitingRoomFragmentBottomSheet
import com.spark.android.ui.waitingroom.makeroomcheckdialog.MakeRoomCheckFragmentDialog
import com.spark.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import com.spark.android.util.KeyBoardUtil.show
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
        if (startPoint != START_FROM_CONFIRM_METHOD) {
            waitingRoomViewModel.getWaitingRoomInfo(roomId)
        }
        initWatitingRoomRecyclerViewAdapter()

        waitingRoomViewModel.waitingRoomInfo.observe(viewLifecycleOwner) {
            updateWatitingRoomRecyclerViewAdapter()
            waitingRoomViewModel.initMemberListSize()
            initClipBoard()
            initTooltipButton()
        }

        initMakeRoomButtonListener()
        initSetPurposeButtonListener()
        initMoveHomeButtonListener()
        initRefreshButtonListener()
        initExtraMenuButton()
    }

    private fun initExtra() {
        roomId = arguments?.getInt("roomId", -1) ?: -1
        startPoint = arguments?.getInt("startPoint", START_FROM_HOME)
            ?: START_FROM_HOME
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
            waitingRoomViewModel.updateMemberListSize()
            binding.btnWaitingRoomRefresh.isEnabled = true
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
            MakeRoomCheckFragmentDialog().show(
                requireActivity().supportFragmentManager, "MakeRoomCheckDialog"
            )
        }
    }

    private fun initSetPurposeButtonListener() {
        binding.btnWaitingRoomEditPurpose.setOnClickListener {

            var bundle = Bundle()
            bundle.putInt("roomId", roomId)
            bundle.putString("roomName", waitingRoomViewModel.waitingRoomInfo.value?.roomName)
            bundle.putString(
                "moment",
                waitingRoomViewModel.waitingRoomInfo.value?.reqUser?.moment ?: ""
            )
            bundle.putString(
                "purpose",
                waitingRoomViewModel.waitingRoomInfo.value?.reqUser?.purpose ?: ""
            )
            bundle.putInt("startPoint", startPoint)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, SetPurposeFragment::class.java, bundle)
                .commit()
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
        }
    }

    private fun initExtraMenuButton() {
        binding.btnWaitingRoomExtraMenu.setOnClickListener {
            val waitingRoomFragmentBottomSheet = WaitingRoomFragmentBottomSheet()
            waitingRoomFragmentBottomSheet.show(
                requireActivity().supportFragmentManager,
                waitingRoomFragmentBottomSheet.tag
            )
        }
    }

    override fun onPause() {
        super.onPause()
        if (::toastAnimation.isInitialized) {
            toastAnimation.cancel()
        }
    }
}
