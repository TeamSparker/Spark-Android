package com.spark.android.ui.makeroom.selectconfirmmethod

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentSelectConfirmMethodBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.makeroom.selectconfirmmethod.viewmodel.SelectConfirmMethodViewModel


class SelectConfirmMethodFragment : BaseFragment<FragmentSelectConfirmMethodBinding>(R.layout.fragment_select_confirm_method) {

    private val selectConfirmMethodViewModel by viewModels<SelectConfirmMethodViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectConfirmMethodViewModel = selectConfirmMethodViewModel
    }
}