package com.teamsparker.android.ui.waitingroom.checkroom

import android.animation.Animator
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.fragment.app.activityViewModels
import com.teamsparker.android.R
import com.teamsparker.android.databinding.FragmentCheckRoomBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.waitingroom.WaitingRoomActivity.Companion.START_FROM_CONFIRM_METHOD
import com.teamsparker.android.ui.waitingroom.WaitingRoomFragment
import com.teamsparker.android.ui.waitingroom.viewmodel.WaitingRoomViewModel
import com.teamsparker.android.util.AnimationUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class CheckRoomFragment : BaseFragment<FragmentCheckRoomBinding>(R.layout.fragment_check_room) {

    private val checkRoomViewModel by activityViewModels<WaitingRoomViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private var startPoint by Delegates.notNull<Int>()
    private lateinit var toastAnimation: Animator

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
        startPoint = arguments?.getInt("startPoint", START_FROM_CONFIRM_METHOD) ?: START_FROM_CONFIRM_METHOD
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

            binding.btnCheckRoomCopyCode.isClickable = false
            binding.tvCheckRoomToast.visibility = View.VISIBLE
            toastAnimation =
                requireNotNull(AnimationUtil.grayBoxToastAnimation(binding.tvCheckRoomToast)).apply {
                    doOnEnd {
                        binding.tvCheckRoomToast.visibility = View.GONE
                        binding.btnCheckRoomCopyCode.isClickable = true
                    }
                    start()
                }
        }
    }

    private fun initMoveHomeButton(){
        binding.btnCheckRoomMoveHome.setOnClickListener{
            requireActivity().finish()
        }
    }

    private fun initMoveWaitingRoomButton(){
        binding.btnCheckRoomMoveWaitingRoom.setOnClickListener {

            var bundle = Bundle()
            bundle.putInt("roomId", roomId)
            bundle.putInt("startPoint", startPoint)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, WaitingRoomFragment::class.java,bundle).commit()
        }
    }

    override fun onPause() {
        super.onPause()
        if(::toastAnimation.isInitialized) {
            toastAnimation.cancel()
        }
    }
}
