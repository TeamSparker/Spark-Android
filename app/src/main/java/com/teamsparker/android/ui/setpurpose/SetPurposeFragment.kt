package com.teamsparker.android.ui.setpurpose

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.teamsparker.android.R
import com.teamsparker.android.data.remote.entity.request.SetPurposeRequest
import com.teamsparker.android.databinding.FragmentSetPurposeBinding
import com.teamsparker.android.ui.base.BaseFragment
import com.teamsparker.android.ui.setpurpose.viewmodel.SetPurposeViewModel
import com.teamsparker.android.ui.waitingroom.WaitingRoomActivity.Companion.START_FROM_JOIN_CODE
import com.teamsparker.android.ui.waitingroom.WaitingRoomFragment
import com.teamsparker.android.util.AnimationUtil
import com.teamsparker.android.util.EventObserver
import com.teamsparker.android.util.KeyBoardUtil
import com.teamsparker.android.util.KeyboardVisibilityUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SetPurposeFragment : BaseFragment<FragmentSetPurposeBinding>(R.layout.fragment_set_purpose) {

    private val setPurposeViewModel by viewModels<SetPurposeViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private lateinit var roomName: String
    private lateinit var moment: String
    private lateinit var purpose: String
    private var startPoint by Delegates.notNull<Int>()
    private var layoutState = false
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils
    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                var bundle = Bundle()
                bundle.putInt("roomId", roomId)
                bundle.putInt("startPoint", startPoint)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container_waiting_room, WaitingRoomFragment::class.java, bundle)
                    .commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraData()
        binding.roomName = roomName
        binding.setPurposeViewModel = setPurposeViewModel
        initLastPurpose()
        initEditTextClearFocus()
        initPurposeEditTextFocusListener()
        initWhenEditTextFocusListener()
        initSettingPurposeBackButton()
        initSettingPurposeFinish()
        initWhenEditTextTouchListener()
        initPurposeEditTextTouchListener()
        initKeyBoardEvent()
    }

    private fun getExtraData() {
        roomId = arguments?.getInt("roomId", -1) ?: -1
        roomName = arguments?.getString("roomName").toString()
        moment = arguments?.getString("moment").toString()
        purpose = arguments?.getString("purpose").toString()
        startPoint = arguments?.getInt("startPoint") ?: START_FROM_JOIN_CODE
    }

    private fun initLastPurpose() {
        setPurposeViewModel.setLastPurpose(moment, purpose)
    }

    private fun initEditTextClearFocus() {
        binding.layoutSetPurpose.setOnClickListener {
            layoutState = false
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun initWhenEditTextTouchListener() {
        binding.etSetPurposeWhen.setOnClickListener {
            if (!layoutState) {
                AnimationUtil.getFocusInSetPurpose(
                    binding.tvSetPurposeExplainOne,
                    binding.tvSetPurposeExplainTwo,
                    binding.etSetPurposeWhen,
                    binding.layoutSetPurposeMoving,
                    requireActivity(),
                    binding.layoutSetPurpose
                )
                layoutState = true
            } else {
                binding.etSetPurposeWhen.isFocusableInTouchMode = true
                binding.etSetPurposeWhen.requestFocus()
                binding.etSetPurposeWhen.isCursorVisible = true
                KeyBoardUtil.show(requireActivity())
            }
        }
    }

    private fun initWhenEditTextFocusListener() {
        binding.etSetPurposeWhen.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewSetPurposeOne.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.viewSetPurposeOne.context,
                        R.color.spark_pinkred
                    )
                )
            } else {
                if (binding.etSetPurposeWhen.text.isEmpty()) {
                    binding.viewSetPurposeOne.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.viewSetPurposeOne.context,
                            R.color.spark_gray
                        )
                    )
                }
                binding.etSetPurposeWhen.isCursorVisible = false
                binding.etSetPurposeWhen.isFocusableInTouchMode = false
            }
        }
    }

    private fun initPurposeEditTextTouchListener() {
        binding.etSetPurposeMyPurpose.setOnClickListener {
            if (!layoutState) {
                AnimationUtil.getFocusInSetPurpose(
                    binding.tvSetPurposeExplainOne,
                    binding.tvSetPurposeExplainTwo,
                    binding.etSetPurposeMyPurpose,
                    binding.layoutSetPurposeMoving,
                    requireActivity(),
                    binding.layoutSetPurpose
                )
                layoutState = true
            } else {
                binding.etSetPurposeMyPurpose.isFocusableInTouchMode = true
                binding.etSetPurposeMyPurpose.requestFocus()
                binding.etSetPurposeMyPurpose.isCursorVisible = true
                KeyBoardUtil.show(requireActivity())
            }
        }
    }

    private fun initPurposeEditTextFocusListener() {
        binding.etSetPurposeMyPurpose.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.viewSetPurposeTwo.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.viewSetPurposeOne.context,
                        R.color.spark_pinkred
                    )
                )
            } else {
                if (binding.etSetPurposeMyPurpose.text.isEmpty()) {
                    binding.viewSetPurposeTwo.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.viewSetPurposeOne.context,
                            R.color.spark_gray
                        )
                    )
                }
                binding.etSetPurposeMyPurpose.isCursorVisible = false
                binding.etSetPurposeMyPurpose.isFocusableInTouchMode = false
            }
        }
    }

    private fun initKeyBoardEvent() {
        keyboardVisibilityUtils = KeyboardVisibilityUtils(requireActivity().window,
            onHideKeyboard = {
                AnimationUtil.lostFocusInSetPurpose(
                    binding.tvSetPurposeExplainOne,
                    binding.tvSetPurposeExplainTwo,
                    binding.etSetPurposeWhen,
                    binding.etSetPurposeMyPurpose,
                    binding.layoutSetPurposeMoving
                )
                layoutState = false
                requireActivity().currentFocus?.clearFocus()
            }
        )
    }

    private fun initSettingPurposeFinish() {
        binding.btnSetPurposeFinish.setOnClickListener {
            binding.btnSetPurposeFinish.isClickable = false
            setPurposeViewModel.setPurpose(
                roomId,
                SetPurposeRequest(
                    setPurposeViewModel.habitWhen.value!!,
                    setPurposeViewModel.myPurpose.value!!
                )
            )
            setPurposeViewModel.networkState.observe(viewLifecycleOwner, EventObserver {

                val bundle = Bundle()
                bundle.putInt("roomId", roomId)
                bundle.putInt("startPoint", startPoint)
                bundle.putBoolean("setPurposeEvent", true)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container_waiting_room, WaitingRoomFragment::class.java, bundle)
                    .commit()
            })
        }
    }

    private fun initSettingPurposeBackButton() {
        binding.btnSetPurposeQuit.setOnClickListener {

            val bundle = Bundle()
            bundle.putInt("roomId", roomId)
            bundle.putInt("startPoint", startPoint)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, WaitingRoomFragment::class.java, bundle)
                .commit()
        }
    }

    override fun onDestroyView() {
        keyboardVisibilityUtils.detachKeyboardListeners()
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}
