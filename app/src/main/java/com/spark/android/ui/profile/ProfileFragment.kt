package com.spark.android.ui.profile

import android.os.Bundle
import android.view.View
import com.spark.android.R
import com.spark.android.databinding.FragmentProfileBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.util.initStatusBarColor
import com.spark.android.util.initStatusBarTextColorToWhite

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().initStatusBarColor(R.color.spark_white)
        requireActivity().initStatusBarTextColorToWhite()
    }
}
