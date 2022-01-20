package com.spark.android.ui.setpurpose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.data.remote.entity.request.SetPurposeRequest
import com.spark.android.databinding.FragmentSetPurposeBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.setpurpose.viewmodel.SetPurposeViewModel
import com.spark.android.ui.waitingroom.WaitingRoomFragment
import com.spark.android.util.KeyBoardUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class SetPurposeFragment : BaseFragment<FragmentSetPurposeBinding>(R.layout.fragment_set_purpose) {

    private val setPurposeViewModel by viewModels<SetPurposeViewModel>()
    private var roomId by Delegates.notNull<Int>()
    private lateinit var roomName :String


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

    private fun initWhenEditTextFocusListener() {
        binding.etSetPurposeWhen.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.tvSetPurposeExplainOne.visibility = View.GONE
                binding.tvSetPurposeExplainTwo.visibility = View.GONE
            } else {
                binding.tvSetPurposeExplainOne.visibility = View.VISIBLE
                binding.tvSetPurposeExplainTwo.visibility = View.VISIBLE
            }
        }
    }

    private fun initPurposeEditTextFocusListener() {
        binding.etSetPurposeMyPurpose.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.tvSetPurposeExplainOne.visibility = View.GONE
                binding.tvSetPurposeExplainTwo.visibility = View.GONE
            } else {
                binding.tvSetPurposeExplainOne.visibility = View.VISIBLE
                binding.tvSetPurposeExplainTwo.visibility = View.VISIBLE
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
            waitingRoomFragment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_waiting_room, waitingRoomFragment).commit()
        }
    }
}