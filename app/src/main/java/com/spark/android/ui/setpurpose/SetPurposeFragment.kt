package com.spark.android.ui.setpurpose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.spark.android.R
import com.spark.android.databinding.FragmentSetPurposeBinding
import com.spark.android.ui.base.BaseFragment
import com.spark.android.ui.setpurpose.viewmodel.SetPurposeViewModel
import com.spark.android.util.KeyBoardUtil


class SetPurposeFragment : BaseFragment<FragmentSetPurposeBinding>(R.layout.fragment_set_purpose) {

    private val setPurposeViewModel by viewModels<SetPurposeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.setPurposeViewModel = setPurposeViewModel
        initEditTextClearFocus()
        initPurposeEditTextFocusListener()
        initWhenEditTextFocusListener()
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
}