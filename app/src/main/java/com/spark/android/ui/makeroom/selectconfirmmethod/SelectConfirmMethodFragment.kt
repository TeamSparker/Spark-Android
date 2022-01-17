package com.spark.android.ui.makeroom.selectconfirmmethod

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.spark.android.R
import com.spark.android.databinding.FragmentSelectConfirmMethodBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.makeroom.selectconfirmmethod.viewmodel.SelectConfirmMethodViewModel
import com.spark.android.ui.waitingroom.WaitingRoomActivity
import com.spark.android.ui.waitingroom.WaitingRoomFragment
import com.spark.android.util.popBackStack


class SelectConfirmMethodFragment : BaseFragment<FragmentSelectConfirmMethodBinding>(R.layout.fragment_select_confirm_method) {

    private val selectConfirmMethodViewModel by viewModels<SelectConfirmMethodViewModel>()
    private val roomName by navArgs<SelectConfirmMethodFragmentArgs>()
    private val roomid = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectConfirmMethodViewModel = selectConfirmMethodViewModel
        initOpenWaitingRoom()
        initBackButton()
    }

    private fun initOpenWaitingRoom(){
        binding.btnMakeRoomSelectConfirmMethodEnterWaiting.setOnClickListener {
            val intent = Intent(requireActivity(),WaitingRoomActivity::class.java).apply {
                putExtra("roomId",roomid)
            }
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun initBackButton(){
        binding.btnMakeRoomSelectConfirmMethodBack.setOnClickListener {
            popBackStack()
        }
    }
}