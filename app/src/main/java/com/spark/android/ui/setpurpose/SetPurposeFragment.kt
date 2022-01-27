package com.spark.android.ui.setpurpose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SetPurposeFragment : BaseFragment<FragmentSetPurposeBinding>(R.layout.fragment_set_purpose) {

    private val setPurposeViewModel by viewModels<SetPurposeViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private lateinit var roomName: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getExtraData()
        binding.roomName = roomName
        binding.setPurposeViewModel = setPurposeViewModel
        initEditTextClearFocus()
        initPurposeEditTextFocusListener()
        initWhenEditTextFocusListener()
        initsettingPurposeBackButton()
        initsettingPurposeFinish()
        initWhenEditTextTouchListener()
        initPurposeEditTextTouchListener()
    }


    private fun getExtraData() {
        roomId = arguments?.getInt("roomId", -1) ?: -1
        roomName = arguments?.getString("roomName").toString()
    }


    private fun initEditTextClearFocus() {
        binding.layoutSetPurpose.setOnClickListener {
            KeyBoardUtil.hide(requireActivity())
        }
    }

    private fun initWhenEditTextTouchListener() {
        binding.etSetPurposeWhen.setOnClickListener {
            AnimationUtil.getFocusInSetPurpose(
                binding.tvSetPurposeExplainOne,
                binding.tvSetPurposeExplainTwo,
                binding.etSetPurposeWhen,
                binding.layoutSetPurposeMoving
            )
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
                AnimationUtil.lostFocusInSetPurpose(
                    binding.tvSetPurposeExplainOne,
                    binding.tvSetPurposeExplainTwo,
                    binding.layoutSetPurposeMoving
                )
                binding.etSetPurposeWhen.isCursorVisible = false
            }
        }
    }

    private fun initPurposeEditTextTouchListener() {
        binding.etSetPurposeMyPurpose.setOnClickListener {
            AnimationUtil.getFocusInSetPurpose(
                binding.tvSetPurposeExplainOne,
                binding.tvSetPurposeExplainTwo,
                binding.etSetPurposeMyPurpose,
                binding.layoutSetPurposeMoving
            )
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
                AnimationUtil.lostFocusInSetPurpose(
                    binding.tvSetPurposeExplainOne,
                    binding.tvSetPurposeExplainTwo,
                    binding.layoutSetPurposeMoving
                )
                binding.etSetPurposeMyPurpose.isCursorVisible = false
            }
        }
    }

    private fun initsettingPurposeFinish() {
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
}
