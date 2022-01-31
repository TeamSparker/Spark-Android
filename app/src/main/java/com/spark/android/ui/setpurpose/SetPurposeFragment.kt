package com.spark.android.ui.setpurpose

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.databinding.FragmentSetPurposeBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.setpurpose.viewmodel.SetPurposeViewModel
import com.spark.android.ui.waitingroom.WaitingRoomFragment
import com.spark.android.util.AnimationUtil
import com.spark.android.util.EditTextUtil
import com.spark.android.util.KeyBoardUtil
import com.spark.android.util.KeyboardVisibilityUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SetPurposeFragment : BaseFragment<FragmentSetPurposeBinding>(R.layout.fragment_set_purpose) {

    private val setPurposeViewModel by viewModels<SetPurposeViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private lateinit var roomName: String
    private var layoutState = false
    private lateinit var keyboardVisibilityUtils: KeyboardVisibilityUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExtraData()
        binding.roomName = roomName
        binding.setPurposeViewModel = setPurposeViewModel
        initEditTextClearFocus()
        initPurposeEditTextFocusListener()
        initWhenEditTextFocusListener()
        initsettingPurposeBackButton()
        initSettingPurposeFinish()
        initWhenEditTextTouchListener()
        initPurposeEditTextTouchListener()
        initKeyBoardEvent()
    }

    private fun getExtraData() {
        roomId = arguments?.getInt("roomId", -1) ?: -1
        roomName = arguments?.getString("roomName").toString()
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
            setPurposeViewModel.setPurpose(
                roomId,
                SetPurposeRequest(
                    setPurposeViewModel.habitWhen.value!!,
                    setPurposeViewModel.myPurpose.value!!
                )
            )
            setPurposeViewModel.networkState.observe(this) {
                val waitingRoomFragment = WaitingRoomFragment()

                var bundle = Bundle()
                bundle.putInt("roomId", roomId)
                bundle.putBoolean("startPoint", true)
                waitingRoomFragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container_waiting_room, waitingRoomFragment).commit()
            }
        }
    }

    private fun initsettingPurposeBackButton() {
        binding.btnSetPurposeQuit.setOnClickListener {
            val waitingRoomFragment = WaitingRoomFragment()

            var bundle = Bundle()
            bundle.putInt("roomId", roomId)
            bundle.putBoolean("startPoint", true)
            waitingRoomFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, waitingRoomFragment).commit()
        }
    }

    override fun onDestroyView() {
        keyboardVisibilityUtils.detachKeyboardListeners()
    }
}
