package com.spark.android.ui.makeroom.namesetting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentNameSettingBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.makeroom.namesetting.viewmodel.NameSettingViewModel


class NameSettingFragment : BaseFragment<FragmentNameSettingBinding>(R.layout.fragment_name_setting) {

    private val nameSettingViewModel by viewModels<NameSettingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nameSettingViewModel = nameSettingViewModel
    }
}